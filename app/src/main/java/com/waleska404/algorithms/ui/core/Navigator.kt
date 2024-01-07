package com.waleska404.algorithms.ui.core

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.waleska404.algorithms.ui.bubblesort.BubbleSortScreen
import com.waleska404.algorithms.ui.dijkstra.DijkstraScreen
import com.waleska404.algorithms.ui.home.HomeScreen
import com.waleska404.algorithms.ui.linearsearch.LinearSearchScreen
import com.waleska404.algorithms.ui.quicksort.QuickSortScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentWrapper(navigationController: NavHostController) {
    NavHost(
        navController = navigationController,
        startDestination = Routes.Home.route,
    ) {
        composable(route = Routes.Home.route) {
            HomeScreen(
                navigateToBubbleSort = {
                    navigationController.navigate(
                        Routes.BubbleSort.route
                    )
                },
                navigateToQuickSort = {
                    navigationController.navigate(
                        Routes.QuickSort.route
                    )
                },
                navigateToDijkstra = {
                    navigationController.navigate(
                        Routes.Dijkstra.route
                    )
                },
                navigateToLinearSearch = {
                    navigationController.navigate(
                        Routes.LinearSearch.route
                    )
                }
            )

        }
        composable(route = Routes.BubbleSort.route) {
            BubbleSortScreen(
                navigateToHome = {
                    navigationController.popBackStack()
                }
            )
        }
        composable(route = Routes.QuickSort.route) {
            QuickSortScreen(
                navigateToHome = {
                    navigationController.popBackStack()
                }
            )
        }
        composable(route = Routes.Dijkstra.route) {
            DijkstraScreen(
                navigateToHome = {
                    navigationController.popBackStack()
                }
            )
        }
        composable(route = Routes.LinearSearch.route) {
            LinearSearchScreen(
                navigateToHome = {
                    navigationController.popBackStack()
                }
            )
        }
    }
}

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object BubbleSort : Routes("bubble_sort")
    object QuickSort : Routes("quick_sort")
    object Dijkstra : Routes("dijkstras_algorithm")
    object LinearSearch : Routes("linear_search")
}