package com.fabienit.aeroclubpassion;

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
            .importPackages("com.fabienit.aeroclubpassion");

        noClasses()
            .that()
            .resideInAnyPackage("com.fabienit.aeroclubpassion.service..")
            .or()
            .resideInAnyPackage("com.fabienit.aeroclubpassion.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.fabienit.aeroclubpassion.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
