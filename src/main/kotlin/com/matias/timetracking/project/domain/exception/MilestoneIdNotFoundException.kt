package com.matias.timetracking.project.domain.exception

import java.util.*

class MilestoneIdNotFoundException(milestoneId: UUID) :
    DomainException("Milestone with id '$milestoneId' not found")