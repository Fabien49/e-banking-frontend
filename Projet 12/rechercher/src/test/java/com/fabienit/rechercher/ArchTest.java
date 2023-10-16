package com.fabienit.rechercher;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.fabienit.rechercher");

        noClasses()
            .that()
            .resideInAnyPackage("com.fabienit.rechercher.service..")
            .or()
            .resideInAnyPackage("com.fabienit.rechercher.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.fabienit.rechercher.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
