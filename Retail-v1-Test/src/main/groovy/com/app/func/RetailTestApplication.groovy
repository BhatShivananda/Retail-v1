package com.app.func

import org.junit.runner.JUnitCore
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RetailTestApplication {
    private static final Logger log = LoggerFactory.getLogger(RetailTestApplication.class)

    enum Suites {
        RETAILAPP("com.app.func.suites.RetailTestSuite")


        public String packageName

        Suites(String packageName) {
            this.packageName = packageName
        }

        static fromString = { String suiteName ->
            Arrays.stream(values())
                    .filter({ suite -> suite.name() == suiteName.toUpperCase() })
                    .findFirst()
                    .orElse(null)
        }
    }

    RetailTestApplication(ApplicationArguments args) {
        String[] suites = args.getSourceArgs() ?: [Suites.RETAILAPP.name()]

        log.info("Running suites {}", suites)

        String[] suitePackageNames = Arrays.stream(suites)
                .map({ suite -> Suites.fromString(suite) })
                .filter({ suite -> Objects.nonNull(suite) })
                .map({ suite -> suite.packageName })
                .toArray()

        JUnitCore.main(suitePackageNames)
    }


    static void main(String[] args) {

        SpringApplication.run(RetailTestApplication.class, args)
    }
}

