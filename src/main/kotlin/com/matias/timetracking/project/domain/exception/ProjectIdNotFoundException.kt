package com.matias.timetracking.project.domain.exception

import java.util.*

class ProjectIdNotFoundException(projectId: UUID) :
    DomainException("Project with ID $projectId not found")