package com.matias.timetracking.project.infrastructure.dao

import com.matias.timetracking.project.infrastructure.dao.row.ProjectRow
import org.springframework.data.repository.ListCrudRepository
import java.util.*

interface ProjectDao : ListCrudRepository<ProjectRow, UUID>
