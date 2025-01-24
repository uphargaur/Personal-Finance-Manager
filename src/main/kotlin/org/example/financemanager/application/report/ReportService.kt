package org.example.financemanager.application.report

import org.example.financemanager.domain.Report.ReportResponse

interface ReportService {

    fun getReport(userId: String, month: Int?, year: Int?): ReportResponse
}
