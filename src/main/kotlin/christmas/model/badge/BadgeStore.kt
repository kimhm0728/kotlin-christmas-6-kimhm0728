package christmas.model.badge

import christmas.constants.Badge

class BadgeStore(private val badge: Badge) {
    override fun toString() = badge.toString()
}