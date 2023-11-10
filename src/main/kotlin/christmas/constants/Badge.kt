package christmas.constants

enum class Badge(private val badgeName: String, val minimumCost: Int) {
    SANTA("산타", 20000),
    TREE("트리", 10000),
    STAR("별", 5000),
    NO_BADGE("없음", 0);

    override fun toString() = badgeName
}