package com.sc.easycooking.recipes.impl.ui.hashtag

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sc.easycooking.view_ext.views.SimpleFlowRow

/*
Another possible depricated solution:
FlowRow(
        modifier = Modifier.padding(8.dp),
        mainAxisAlignment = MainAxisAlignment.Center,
        mainAxisSize = SizeMode.Expand,
        crossAxisSpacing = 12.dp,
        mainAxisSpacing = 8.dp
    ) {
        hashTags.forEach { hashTag ->
            Text(
                text = hashTag,
                modifier = Modifier
                    .background(
                        color = colorForHashTag(hashTag),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(8.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
 */

@Composable
fun HashTagRow(tags: Collection<Int>) {
    SimpleFlowRow(
        verticalGap = 2.dp,
        horizontalGap = 2.dp,
        alignment = Alignment.Start,
    ) {
        for (tag in tags) {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 2.dp, horizontal = 4.dp),
                text = stringResource(id = tag),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )
        }
    }
}