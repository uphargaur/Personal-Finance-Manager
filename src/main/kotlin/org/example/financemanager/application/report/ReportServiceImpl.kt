package org.example.financemanager.application.report

import org.example.financemanager.application.report.ReportService
import org.example.financemanager.domain.Report.ReportResponse
import org.example.financemanager.domain.Report.VisualRepresentation
import org.example.financemanager.domain.transactions.TransactionDetail
import org.example.financemanager.domain.transactions.UserTransactions
import org.example.financemanager.repository.TransactionRepository
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Base64

@Service
class ReportServiceImpl(
    private val transactionRepository: TransactionRepository
) : ReportService {

    override fun getReport(userId: String, month: Int?, year: Int?): ReportResponse {
        val currentDate = LocalDate.now()

        val targetMonth = month ?: currentDate.monthValue
        val targetYear = year ?: currentDate.year

        val userTransactions = transactionRepository.findByUserIdAndYearAndMonth(userId, targetYear, targetMonth)

        val transactions = userTransactions.flatMap { userTransaction ->
            userTransaction.transactions.flatMap { entry -> entry.value }
        }

        val totalIncome = transactions.filter { it.amount > 0 }.sumOf { it.amount }
        val totalExpenses = transactions.filter { it.amount < 0 }.sumOf { it.amount }
        val savings = totalIncome - totalExpenses

        val categoryBreakdown = getCategoryBreakdown(userTransactions)

        return ReportResponse(
            totalIncome = totalIncome,
            totalExpenses = totalExpenses,
            savings = savings,
            categoryBreakdown = categoryBreakdown,
            visualRepresentation = generateVisualRepresentation(transactions)
        )
    }

    private fun getCategoryBreakdown(userTransactions: List<UserTransactions>): Map<String, Double> {
        return userTransactions.flatMap { userTransaction ->
            userTransaction.transactions.map { entry ->
                // Ensure that entry.key is treated as a String
                entry.key.toString() to entry.value.sumOf { it.amount }
            }
        }.toMap()
    }

    private fun generateVisualRepresentation(transactions: List<TransactionDetail>): VisualRepresentation {
        // Generate the visual representations (charts) as base64 strings
        val pieChartBase64 = generatePieChart(transactions)
        val barGraphBase64 = generateBarGraph(transactions)

        return VisualRepresentation(pieChart = pieChartBase64, barGraph = barGraphBase64)
    }

    private fun generatePieChart(transactions: List<TransactionDetail>): String {
        val dataset = createDataset(transactions)
        val chart = ChartFactory.createPieChart(
            "Spending Distribution",
            dataset,
            true,
            true,
            false
        )

        val image = ChartUtils.encodeAsPNG(chart.createBufferedImage(800, 600))
        return Base64.getEncoder().encodeToString(image)
    }

    private fun generateBarGraph(transactions: List<TransactionDetail>): String {
        val dataset = createBarDataset(transactions)
        val chart = ChartFactory.createBarChart(
            "Monthly Expenses",
            "Category",
            "Amount",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )

        val image = ChartUtils.encodeAsPNG(chart.createBufferedImage(800, 600))
        return Base64.getEncoder().encodeToString(image)
    }

    private fun createDataset(transactions: List<TransactionDetail>): DefaultPieDataset<String> {
        val dataset = DefaultPieDataset<String>()  // Specify that it's a String type dataset

        val categorySums = transactions.groupBy { it.description }.mapValues { entry ->
            entry.value.sumOf { it.amount }
        }
        categorySums.forEach { (category, totalAmount) ->
            dataset.setValue(category, totalAmount)
        }
        return dataset
    }

    private fun createBarDataset(transactions: List<TransactionDetail>): DefaultCategoryDataset {
        val dataset = DefaultCategoryDataset()

        val categorySums = transactions.groupBy { it.description }.mapValues { entry ->
            entry.value.sumOf { it.amount }
        }

        // Populate the bar dataset
        categorySums.forEach { (category, totalAmount) ->
            dataset.addValue(totalAmount, "Expenses", category)
        }

        return dataset
    }
}
