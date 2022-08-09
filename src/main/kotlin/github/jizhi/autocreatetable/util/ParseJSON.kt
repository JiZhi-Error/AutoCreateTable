package github.jizhi.autocreatetable.util

import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import java.io.BufferedReader
import java.io.FileReader

/**
 * @author  JiZhi
 * @date  2022/8/9 7:30
 * @version  V1.0
 **/
class ParseJSON {
    fun get(file: String) {
        // TODO 先读一行，后续再全文读
        val readLine = BufferedReader(FileReader(file)).readLine()
        val parseObject = JSONObject.parseObject(readLine)
        val parse = parse(parseObject)
        println(parse)

    }


    // TODO 需要分节步骤，目前会导致外层和里层列类型拼串格式一致，其实应该两种格式拼串
    private fun parse(data: Any): Any {

        var columnData = ""
        if (data is JSONObject) {
            data.forEach { k, v ->
                when (v) {
                    is JSONObject -> {
                        columnData += "$k  struct<${parse(v)}>, "
                    }
                    is JSONArray -> {
                        columnData += "$k  array<${parse(v)}>, "
                    }
                    is String -> {
                        columnData += "$k  :string, "
                    }
                    is Int -> {
                        columnData += "$k  :int, "
                    }
                    is Long -> {
                        columnData += "$k  :bigint, "
                    }
                }
            }
        } else if (data is JSONArray) {
            if (data.isNotEmpty()) {
                columnData += "struct<${parse(data[0]!!)}>  "
            }
        }

        return columnData.subSequence(0, columnData.lastIndex -1 )
    }

}
