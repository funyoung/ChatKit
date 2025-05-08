//package com.stfalcon.chatkit.sample.features.demo.markdown
//
//import android.os.Bundle
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.stfalcon.chatkit.sample.R
//import io.noties.markwon.Markwon
//import io.noties.markwon.ext.tables.TableAwareMovementMethod
//
//import io.noties.markwon.ext.tables.TablePlugin
//import io.noties.markwon.linkify.LinkifyPlugin
//import io.noties.markwon.movement.MovementMethodPlugin
//
//
//class MarkDownTestActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_test)
//
//        val text=findViewById<TextView>(R.id.text)
////         renderMarkdown(text,"**详细数据:**\n" +
////                 "                                                                                                    \n" +
////                 "                                                                                                    | 房型 | 门市价 | 房型总数 | 退房订单数 | 入住订单数 | 空闲房间数 | 入住率 | 房型收入 |\n" +
////                 "                                                                                                    | :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |\n" +
////                 "                                                                                                    | 商务大床房 | 158 | 20 | 8 | 13 | 7 | 65.0% | 1989.0 |\n" +
////                 "                                                                                                    | 标准大床房 | 158 | 19 | 7 | 19 | 0 | 100.0% | 2358.0 |\n" +
////                 "                                                                                                    | 精致双床房 | 188 | 2 | 1 | 2 | 0 | 100.0% | 292.0 |\n" +
////                 "                                                                                                    | 豪华商务双床房 | 198 | 5 | 4 | 5 | 0 | 100.0% | 782.0 |\n" +
////                 "                                                                                                    | 特价房 | 128 | 1 | 1 | 1 | 0 | 100.0% | 150.0 |")
//        render(text)
//
//    }
//    fun renderMarkdown(textView: TextView?, markdown: String?) {
//        val markwon = Markwon.builder(applicationContext)
//            .usePlugin(TablePlugin.create(applicationContext))
//            .build()
//
//        markwon.setMarkdown(textView!!, markdown!!)
//    }
//
//    fun render(textView: TextView) {
//
//        val md: String = """
//        | HEADER | HEADER | HEADER |
//        |:----:|:----:|:----:|
//        |   测试  |   测试   |   测试   |
//        |   测试  |   测试   |  测测测12345试测试测试   |
//        |   测试  |   测试   |   123445   |
//        |   测试  |   测试   |   (650) 555-1212   |
//        |   测试  |   测试   |   mail@ma.il   |
//        |   测试  |   测试   |   some text that goes here is very very very very important [link](https://noties.io/Markwon)   |
//
//        测试
//
//        [link link](https://link.link)
//        """.trimIndent()
//
//
//        val md2:String = """
//        | 房型        | 门市价   | 房型总数 | 退房订单数 | 入住订单数      | 空闲房间数 | 入住率 | 房型收入 |
//        |:----------:|:------:|:---:|:---:|:---:|:---:|:---:|:---:|
//        | 商务大床房 | 158 | 20 | 8 | 13 | 7 | 65.0% | 1989.0 |
//        | 标准大床房 | 158 | 19 | 7 | 19 | 0 | 100.0% | 2358.0 |
//        | 精致双床房 | 188 | 2 | 1 | 2 | 0 | 100.0% | 292.0 |
//        | 豪华商务双床房 | 198 | 5 | 4 | 5 | 0 | 100.0% | 782.0 |
//
//        测试
//
//        [link link](https://link.link)
//        """.trimIndent()
//
//
//        val markwon: Markwon = Markwon.builder(applicationContext)
//            .usePlugin(LinkifyPlugin.create())
//            .usePlugin(TablePlugin.create(applicationContext)) // use TableAwareLinkMovementMethod to handle clicks inside tables
//            .usePlugin(MovementMethodPlugin.create(TableAwareMovementMethod.create()))
//            .build()
//
//        markwon.setMarkdown(textView, md2)
//
//    }
//
//
//}