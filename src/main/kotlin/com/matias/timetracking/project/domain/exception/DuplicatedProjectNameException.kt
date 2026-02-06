package com.matias.timetracking.project.domain.exception

class DuplicatedProjectNameException(projectName: String) : DomainException("Duplicated project name $projectName")
