package org.example.financemanager.presentation

import org.example.financemanager.application.report.ReportService
import org.example.financemanager.domain.Report.ReportResponse
import org.example.financemanager.utils.JwtUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@RequestMapping("/reports")
class ReportController(
    private val reportService: ReportService ,
    private val jwtUtil: JwtUtil
) {

    @GetMapping("/generate")
    fun generateReport(
        @RequestHeader("Authorization") token: String,
        @RequestParam("month", required = false) month: Int?,
        @RequestParam("year", required = false) year: Int?
    ): ResponseEntity<ReportResponse> {

        return try {
            val userId = jwtUtil.validateTokenAndGetUserId(token)
            val report = reportService.getReport(userId, month, year)
            ResponseEntity.ok(report)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(null)
        } catch (e: Exception) {
            ResponseEntity.internalServerError().build()
        }
    }
}
