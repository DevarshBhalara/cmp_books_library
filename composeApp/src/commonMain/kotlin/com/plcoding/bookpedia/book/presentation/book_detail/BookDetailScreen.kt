package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.presentation.book_detail.component.BlurredImageBackground
import com.plcoding.bookpedia.book.presentation.book_detail.component.BookChip
import com.plcoding.bookpedia.book.presentation.book_detail.component.ChipSize
import com.plcoding.bookpedia.book.presentation.book_detail.component.TitledContent
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.acos
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel = koinViewModel(),
    onBackClick: () -> Unit
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state,
        onAction =  { action ->
            when(action) {
                BookDetailAction.OnBackClick -> onBackClick()
                BookDetailAction.OnFavourite -> Unit
                is BookDetailAction.OnSelectedBookChange -> Unit
            }
            viewModel.onAction(action)

        }
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) ->  Unit
) {

    BlurredImageBackground(
        imageUrl = state.book?.imageUrl,
        isFavourite = state.isFavourite,
        onFavouriteClick =  {
            onAction(BookDetailAction.OnFavourite)
        },
        onBackClick = {
            onAction(BookDetailAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {

        if(state.book != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = state.book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = state.book.author.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.book.averageRating?.let {
                        TitledContent(
                            title = "Rating",
                        ) {
                            BookChip {
                                Text(
                                    text = "${round(it * 10) / 10.0}"
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = SandYellow
                                )
                            }
                        }
                    }

                    state.book.numPages?.let {
                        TitledContent(
                            title = "Pages",
                        ) {
                            BookChip {
                                Text(
                                    text = it.toString()
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = SandYellow
                                )
                            }
                        }
                    }
                }

                if(state.book.languages.isNotEmpty()){
                    TitledContent(
                        title = "Languages",
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ) {
                            state.book.languages.forEach { language ->
                                BookChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier
                                        .padding(2.dp)
                                ) {
                                    Text(
                                        text = language.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                        }
                    }
                }

                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 24.dp, bottom = 8.dp)
                )
                if(state.isLoading) {
                    CircularProgressIndicator()
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CircularProgressIndicator()
//                    }
                } else {
                    Text(
                        text = if (!state.book.description.isNullOrBlank()) {
                            state.book.description
                        } else {
                            "No description available!"
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if (state.book.description.isNullOrBlank()) {
                            Color.Black.copy(alpha = 0.4f)
                        } else {
                            Color.Black
                        },
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }

            }
        }

    }
    
}