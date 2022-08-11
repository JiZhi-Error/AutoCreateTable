package github.jizhi.autocreatetable.util

import com.alibaba.fastjson2.JSONArray
import com.alibaba.fastjson2.JSONObject
import com.alibaba.fastjson2.toJSONString
import java.io.BufferedReader
import java.io.FileReader
import java.util.LinkedList

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
    private fun parse(data: Any, layer: Int = 0): String {
        return when (data) {
            is JSONObject -> {
                val result = LinkedList<String>()
                data.forEach { k, v ->
                    when (v){
                        is JSONObject -> if (layer<=1) result.add("$k  struct<${parse(v,layer.inc())}>") else result.add("$k  :struct<${parse(v,layer.inc())}>")
                        is JSONArray -> if (layer<=1) result.add("$k  array<${parse(v,layer.inc())}>") else result.add("$k  :array<${parse(v,layer.inc())}>")
                        else -> result.add("$k  ${parse(v,layer.inc())}")
                    }
                }
                result.joinToString(" ,")
            }

            is JSONArray -> {
                "struct<${parse(data[0],layer.inc())}>"
            }

            is String -> if (layer<=1) "string" else ":string"
            is Int -> if (layer<=1) "int" else ":int"
            is Long -> if (layer<=1) "bigint" else ":bigint"
            else -> "null"
        }
    }

}
