package org.example.financemanager.domain.Report

data class ReportResponse(
    val totalIncome: Double,
    val totalExpenses: Double,
    val savings: Double,
    val categoryBreakdown: Map<String, Double>,
    val visualRepresentation: VisualRepresentation
)