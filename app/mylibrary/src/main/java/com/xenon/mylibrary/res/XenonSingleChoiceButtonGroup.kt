package com.xenon.mylibrary.res

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.xenon.mylibrary.theme.QuicksandTitleVariable
import com.xenon.mylibrary.values.IconSizeSmaller
import com.xenon.mylibrary.values.LargeMediumCornerRadius
import com.xenon.mylibrary.values.MassiveCornerRadius
import com.xenon.mylibrary.values.MediumPadding
import com.xenon.mylibrary.values.MinMediumButtonHeight
import com.xenon.mylibrary.values.NoCornerRadius
import com.xenon.mylibrary.values.SmallCornerRadius
import com.xenon.mylibrary.values.SmallSpacing
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.time.Duration.Companion.milliseconds


@Composable
fun <T> XenonSingleChoiceButtonGroup(
    options: List<T>,
    selectedOption: T,
    onOptionSelect: (T) -> Unit,
    label: @Composable (T) -> String,
    modifier: Modifier = Modifier,
    buttonHeight: Dp = MinMediumButtonHeight,
    containerColor: Color = colorScheme.surfaceDim,
    selectedContainerColor: Color = colorScheme.primary,
    contentColor: Color = colorScheme.onSurface,
    selectedContentColor: Color = colorScheme.onPrimary,
    selectedIcon: @Composable ((T) -> Unit)? = {
        Icon(
            imageVector = Icons.Rounded.Check,
            contentDescription = "Selected",
            tint = selectedContentColor,
            modifier = Modifier
                .size(IconSizeSmaller)
        )
    },
    unselectedIcon: @Composable ((T) -> Unit)? = null,
    icon: @Composable (T, Boolean) -> Unit = { option, isSelected ->
        if (isSelected) {
            Box(modifier = Modifier.padding(end = MediumPadding)) {
                selectedIcon?.invoke(option)
            }
        } else if (unselectedIcon != null) {
            Box(modifier = Modifier.padding(end = MediumPadding)) {
                unselectedIcon.invoke(option)
            }
        }
    },
    mainContextFont: FontFamily = QuicksandTitleVariable,
    subContextFont: FontFamily? = null,
) {
    val interactionSources = remember(options) { options.map { MutableInteractionSource() } }

    val pressedStates = remember(options) {
        mutableStateListOf<Boolean>().apply { repeat(options.size) { add(false) } }
    }

    options.forEachIndexed { index, _ ->
        LaunchedEffect(interactionSources[index]) {
            var pressStartTime = 0L
            interactionSources[index].interactions.collect { interaction ->
                when (interaction) {
                    is PressInteraction.Press -> {
                        pressedStates[index] = true
                        pressStartTime = System.currentTimeMillis()
                    }

                    is PressInteraction.Release -> {
                        val duration = System.currentTimeMillis() - pressStartTime
                        if (duration < 200) {
                            delay((200 - duration).milliseconds)
                        }
                        pressedStates[index] = false
                    }

                    is PressInteraction.Cancel -> {
                        pressedStates[index] = false
                    }
                }
            }
        }
    }

    val pressedIndex = pressedStates.indexOfFirst { it }

    Row(
        modifier = modifier.height(buttonHeight),
        horizontalArrangement = Arrangement.spacedBy(SmallSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEachIndexed { index, option ->
            val isSelected = selectedOption == option
            val isPressed = pressedStates[index]

            val targetWeight = if (pressedIndex == -1) {
                1f
            } else {
                if (index == pressedIndex) {
                    1.05f
                } else if (abs(index - pressedIndex) == 1) {
                    val neighbors =
                        if (pressedIndex == 0 || pressedIndex == options.size - 1) 1 else 2
                    1f - (0.05f / neighbors)
                } else {
                    1f
                }
            }

            val weight by animateFloatAsState(
                targetValue = targetWeight,
                label = "weight",
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )

            val targetRadius = when {
                isPressed -> SmallCornerRadius
                isSelected -> LargeMediumCornerRadius
                else -> MassiveCornerRadius
            }

            val cornerRadius by animateDpAsState(
                targetValue = targetRadius,
                label = "cornerRadius",
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )

            val animatedContainerColor by animateColorAsState(
                targetValue = if (isSelected) selectedContainerColor else containerColor,
                label = "containerColor"
            )

            val animatedContentColor by animateColorAsState(
                targetValue = if (isSelected) selectedContentColor else contentColor,
                label = "contentColor"
            )

            Box(
                modifier = Modifier
                    .weight(weight)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(cornerRadius.coerceAtLeast(NoCornerRadius)))
                    .background(animatedContainerColor)
                    .clickable(
                        interactionSource = interactionSources[index],
                        indication = ripple(),
                        onClick = { if (!isSelected) onOptionSelect(option) }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = MediumPadding)
                ) {
                    icon(option, isSelected)
                    Text(
                        text = label(option),
                        fontFamily = mainContextFont,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = animatedContentColor
                    )
                }
            }
        }
    }
}
