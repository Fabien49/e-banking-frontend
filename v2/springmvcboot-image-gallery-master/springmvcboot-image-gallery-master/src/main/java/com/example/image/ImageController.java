package com.example.image;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ImageController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private SiteUserRepository userRepository;
	private ImageRepository imageRepository;
	private Path rootLocation;

	public ImageController(SiteUserRepository userRepository, Path rootLocation, ImageRepository imageRepository) {
		this.userRepository = userRepository;
		this.rootLocation = rootLocation;
		this.imageRepository = imageRepository;
	}

	@GetMapping("/")
	public String listUploadedFiles(Model model, Principal principal) throws Exception {

		if (principal == null) {
			return "redirect:/find";
		}

		SiteUser user = userRepository.findByName(principal.getName()).orElseThrow(() -> new Exception());

		List<String> stringss = user.getImageList().stream()
				.map(image -> this.rootLocation.resolve(image.getName()))
				.map(path -> MvcUriComponentsBuilder
						.fromMethodName(ImageController.class, "serveFile", path.getFileName().toString()).build()
						.toString())				
				.collect(Collectors.toList());


		model.addAttribute("files", stringss);
		logger.info("la liste des images de l'upload est : " +stringss);

		return "upload";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws MalformedURLException {

		Path file = this.rootLocation.resolve(filename);
		Resource resource = new UrlResource(file.toUri());

		return ResponseEntity
				.ok()
				.body(resource);	
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes,
								   Principal principal, String lieu) throws Exception {

		if (file.getSize() == 0) {
			return "redirect:/";
		}

		String uuid = UUID.randomUUID().toString();

		String imagePath = this.rootLocation.resolve(uuid + ".jpg").toString();
		logger.info("le chemin de l'image est : " +imagePath);

		SiteUser user = userRepository.findByName(principal.getName()).orElseThrow(() -> new Exception());
		//TODO
		//user.setLieu

		Set<Image> stringList = user.getImageList();
		Image monImage = new Image();
		monImage.setLieu(lieu);
		monImage.setName(imagePath);
		//stringList.add(new Image(imagePath, lieu));
		stringList.add(monImage);
		logger.info("la liste des images est : " +stringList);
		logger.info("le lieu de l'image est : " +lieu);
		Files.copy(file.getInputStream(), this.rootLocation.resolve(imagePath));

		
		userRepository.save(user);

		return "redirect:/";
	}

	@GetMapping("/find")
	public String findPhotos(Model model) {
		return "findphoto";
	}
	
	@GetMapping("/search")
	public String findPhotos(@RequestParam("name") String name, Model model)  {

		SiteUser user;
		try {
			user = userRepository.findByName(name).orElseThrow(Exception::new);
		} catch (Exception e) {
			return "redirect:/find";
		}

		List<String> userImages = user.getImageList().stream()
				.map(image -> this.rootLocation.resolve(image.getName()))
				.map(path -> MvcUriComponentsBuilder
						.fromMethodName(ImageController.class, "serveFile", path.getFileName().toString()).build()
						.toString())
				.collect(Collectors.toList());

		model.addAttribute("files", userImages);
		logger.info("l'utilisateur de l'image est : " +userImages);
		model.addAttribute("user", user.getName());
		logger.info("le nom de l'image est : " +name);

		return "findphoto";

	}

	@RequestMapping("/delete")
	public String findPhotos(Principal principal, @RequestParam("text") String text, String string) throws Exception {

		SiteUser user = userRepository.findByName(principal.getName()).orElseThrow(() -> new Exception());

		text = text.substring(text.lastIndexOf("/"));
		text = this.rootLocation + text;

		Image image = imageRepository.findByName(text);

		user.getImageList().remove(image);

		userRepository.save(user);

		return "redirect:/";

	}
}
