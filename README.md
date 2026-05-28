# Book Catalog Management

Project for the Automation Software Testing course @ University of Florence

[![Java CI with Maven on Linux](https://github.com/pippodima/bookCatalogTDD/actions/workflows/maven.yml/badge.svg)](https://github.com/pippodima/bookCatalogTDD/actions/workflows/maven.yml)
[![Coverage Status](https://coveralls.io/repos/github/pippodima/bookCatalogTDD/badge.svg)](https://coveralls.io/github/pippodima/bookCatalogTDD)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=coverage)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=bugs)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=filippoast_bookcatalogtdd&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=filippoast_bookcatalogtdd)

## Description

Book Catalog Management is a project designed to manage books and authors through a simple graphical interface. Users can add new authors by providing a unique ID, first name, and last name. The application allows you to view the list of registered authors and remove existing ones, with automatic deletion of their associated books. Similarly, you can add new books by providing an ISBN, title, publication year, and selecting an existing author. Registered books can be viewed and deleted at any time.

## Technologies

- Java 17
- MongoDB
- Swing (GUI)
- JUnit 4
- Mockito
- AssertJ Swing
- Testcontainers
- JaCoCo
- PIT Mutation Testing
- Coveralls
- SonarCloud
- GitHub Actions
