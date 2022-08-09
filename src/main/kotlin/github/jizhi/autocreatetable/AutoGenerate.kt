package github.jizhi.autocreatetable

import github.jizhi.autocreatetable.util.ParseJSON

/**
 * @author JiZhi
 * @version V1.0
 * @date 2022/8/9 7:26
 */
object AutoGenerate {
    @JvmStatic
    fun main(args: Array<String>) {
        ParseJSON().get("data.json")
    }
}
