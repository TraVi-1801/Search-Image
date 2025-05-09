package com.vic_project.search_image.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarDuration.Indefinite
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.vic_project.search_image.data.remote.network.NetworkMonitor
import com.vic_project.search_image.ui.navigate.AppNavHost
import com.vic_project.search_image.ui.navigate.TopLevelDestination
import com.vic_project.search_image.ui.theme.AppTheme
import com.vic_project.search_image.utils.extensions.ModifierExtension.clickableSingle

@Composable
fun App(
    networkMonitor: NetworkMonitor,
    appState: AppState = rememberAppState(
        networkMonitor = networkMonitor,
    )
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    val currentScreen = appState.currentDestination?.route

    val notConnectedMessage = "⚠️ You aren’t connected to the internet"
    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackbarHostState.showSnackbar(
                message = notConnectedMessage,
                duration = Indefinite,
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            if (appState.shouldShowBottomBar) {
                HRMBottomBar(
                    destinations = appState.topLevelDestinations,
                    onNavigateToDestination = appState::navigateToTopLevelDestination,
                    currentDestination = appState.currentDestination,
                )
            }
        },
        content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.neutral20)
                    .padding(PaddingValues(bottom = padding.calculateBottomPadding()))
            ) {
                AppNavHost(
                    appState = appState,
                    onShowSnackbar = { message, action ->
                        snackbarHostState.showSnackbar(
                            message = message,
                            actionLabel = action,
                            duration = SnackbarDuration.Short,
                        ) == SnackbarResult.ActionPerformed
                    },
                )
            }
        },
    )
}

@Composable
private fun HRMBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colors.neutral20)
    ) {
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .background(AppTheme.colors.neutral40, shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.TopCenter)
        )
        Row(
            modifier = Modifier
                .padding(top = 1.dp)
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .navigationBarsPadding()
                .background(AppTheme.colors.neutral10, shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            destinations.forEach {
                /*if(it.titleTextId == TopLevelDestination.Request.titleTextId){
                    if (AuthService.profile?.status == 1){
                        AddItem(
                            screen = it,
                            selected = currentDestination.isTopLevelDestinationInHierarchy(it),
                            onClick = { onNavigateToDestination(it) },
                        )
                    }
                } else {
                    AddItem(
                        screen = it,
                        selected = currentDestination.isTopLevelDestinationInHierarchy(it),
                        onClick = { onNavigateToDestination(it) },
                    )
                }*/
                AddItem(
                    screen = it,
                    selected = currentDestination.isTopLevelDestinationInHierarchy(it),
                    onClick = { onNavigateToDestination(it) },
                )
            }
        }
    }
}

@Composable
fun AddItem(
    screen: TopLevelDestination,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .height(75.dp)
            .width(IntrinsicSize.Max)
            .padding(top = 3.dp)
            .padding(horizontal = 10.dp)
            .clickableSingle {
                onClick.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                painter = painterResource( if (selected) screen.selectedIcon else screen.unselectedIcon ),
                contentDescription = screen.iconTextId,
                tint = if (selected) AppTheme.colors.primaryMain else AppTheme.colors.neutral100
            )

            Text(
                text = screen.titleTextId,
                color = if (selected) AppTheme.colors.primaryMain else AppTheme.colors.neutral100,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
