package com.baidaidai.anycloud.application.notification.liveupdate

import com.baidaidai.anycloud.data.notification.liveupdate.repository.LiveUpdateNotificationRepositoryImpl
import javax.inject.Inject

class UpdateOneLiveUpdateNotificationPositionUseCase @Inject constructor(
    private val liveUpdateNotificationRepositoryImpl: LiveUpdateNotificationRepositoryImpl
) {
    suspend operator fun invoke(
        unixTimeStamp: Long,
        insertionIndex: Int
    ) {
        // Excluding the dragged notification turns insertionIndex into a gap index. For example,
        // index 1 means the gap between weightList[0] and weightList[1].
        val notificationWeightList = liveUpdateNotificationRepositoryImpl
            .getAllLiveUpdateNotificationWeightExcept(unixTimeStamp)
        val safeInsertionIndex = insertionIndex.coerceIn(
            minimumValue = 0,
            maximumValue = notificationWeightList.size
        )
        val previousNotificationWeight = notificationWeightList
            .getOrNull(safeInsertionIndex - 1)
        val nextNotificationWeight = notificationWeightList
            .getOrNull(safeInsertionIndex)

        val newNotificationWeight = when {
            previousNotificationWeight == null && nextNotificationWeight == null -> {
                INITIAL_NOTIFICATION_WEIGHT
            }

            previousNotificationWeight == null -> {
                checkNotNull(nextNotificationWeight) - NOTIFICATION_WEIGHT_GAP
            }

            nextNotificationWeight == null -> {
                checkNotNull(previousNotificationWeight) + NOTIFICATION_WEIGHT_GAP
            }

            else -> {
                // This form avoids overflowing Long when both neighboring weights are large.
                val previousWeight = checkNotNull(previousNotificationWeight)
                val nextWeight = checkNotNull(nextNotificationWeight)

                previousWeight + (nextWeight - previousWeight) / 2L
            }
        }

        liveUpdateNotificationRepositoryImpl.updateOneLiveUpdateNotificationPosition(
            unixTimeStamp = unixTimeStamp,
            notificationWeight = newNotificationWeight
        )
    }

    private companion object {
        const val INITIAL_NOTIFICATION_WEIGHT = 10_000L
        const val NOTIFICATION_WEIGHT_GAP = 10_000L
    }
}