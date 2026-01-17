package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import org.springframework.data.repository.CrudRepository
import java.util.*

interface ProjectDao : CrudRepository<ProjectRow, UUID>