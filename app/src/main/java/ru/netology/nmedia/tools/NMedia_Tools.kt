import java.math.RoundingMode

fun toView(count: Int): String {
    return when (count) {
        in 1..999 -> count.toString()
        in 1000..1099 -> "1K "
        in 1100..10000 -> ((count.toFloat() / 1000).toBigDecimal().setScale(1, RoundingMode.DOWN)
            .toString()) + "K "

        in 10001..999999 -> ((count.toFloat() / 1000).toBigDecimal().setScale(0, RoundingMode.DOWN)
            .toString()) + "K "

        in 1000000..1099999 -> "1M "
        in 1100000..100000000 -> ((count.toFloat() / 1000000).toBigDecimal()
            .setScale(0, RoundingMode.DOWN)).toString() + "M "

        else -> "0"
    }
}