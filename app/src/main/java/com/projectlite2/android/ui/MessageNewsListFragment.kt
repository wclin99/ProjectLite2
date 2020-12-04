package com.projectlite2.android.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.projectlite2.android.MaxRecyclerView
import com.projectlite2.android.Msg
import com.projectlite2.android.R
import com.projectlite2.android.adapter.MessageCardAdapter
import com.projectlite2.android.adapter.MsgChatAdapter
import com.projectlite2.android.app.MyApplication
import com.projectlite2.android.model.MessageCard
import com.projectlite2.android.utils.OnItemClickListenerPlus
import com.projectlite2.android.utils.SimpleItemTouchHelperCallback
import kotlinx.android.synthetic.main.message_card_item.*
import java.util.*

class MessageNewsListFragment() : Fragment() {

    lateinit var mView: View
    lateinit var mRecyclerview: RecyclerView
    lateinit var mAdapter: MessageCardAdapter
    lateinit var mCallBack: ItemTouchHelper.Callback

    private val mMessageList = ArrayList<MessageCard>()
    private val mEveryCardMsgList=ArrayList<ArrayList<Msg>>()
    private val mEveryCardMsgAdapterList = ArrayList<MsgChatAdapter>()

//    lateinit var cRecyclerView: MaxRecyclerView
    lateinit var cAdapter: MsgChatAdapter
    private val msgList = ArrayList<Msg>()
    private var CardClick = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_message_list, container, false)
        return mView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addNewCards()

        //消息卡片
        val mlayoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        mRecyclerview = mView.findViewById(R.id.recyclerViewCardMessage)
        mRecyclerview.layoutManager = mlayoutManager
        mAdapter = MessageCardAdapter(mMessageList)
        mRecyclerview.adapter = mAdapter

        mRecyclerview.itemAnimator = DefaultItemAnimator()
        mRecyclerview.itemAnimator!!.changeDuration = 300


        //先实例化Callback
        mCallBack = SimpleItemTouchHelperCallback(mAdapter)
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        ItemTouchHelper(mCallBack).attachToRecyclerView(mRecyclerview)




        mAdapter.setOnKotlinItemClickListener(object : OnItemClickListenerPlus {
            override fun onClick(item: View?, position: Int, which: Int) {

                when (which) {
                    R.id.messageCardBackground -> {

                        val thisCardView = mRecyclerview.getChildAt(position)
                        val thisRV = thisCardView.findViewById<MaxRecyclerView>(R.id.messageBox)



                        if (CardClick) {

                            thisRV.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                            thisRV.adapter = mEveryCardMsgAdapterList[position]
                            val msg0 = Msg("报告这周六交", Msg.TYPE_RECEIVED)
                            mEveryCardMsgList[position].add(msg0)

                            CardClick = false
                        } else {
                            thisRV.scrollToPosition(mEveryCardMsgList[position].size - 1)
                        }

                    }
                    R.id.btnReply -> {

                        //  测试
                        MyApplication.ToastyInfo("$position")

                        val thisCardView = mRecyclerview.getChildAt(position)

                        // 测试
                        val string = thisCardView.findViewById<TextView>(R.id.txtName).text.toString()
                        MyApplication.ToastyInfo(string)

                        val thisRV = thisCardView.findViewById<MaxRecyclerView>(R.id.messageBox)
                        //测试
//                        MyApplication.ToastyInfo(thisCardView.findViewById<TextView>(R.id.txtName).id.toString())
//                        MyApplication.ToastyInfo(thisRV.id.toString())


                        thisRV.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                        thisRV.adapter = mEveryCardMsgAdapterList[position]


                        val thisReplyMsg = thisCardView.findViewById<EditText>(R.id.textReply).text.toString()

                        if (thisReplyMsg.isNotEmpty()) {
                            val msg = Msg(thisReplyMsg, Msg.TYPE_SENT)
                            mEveryCardMsgList[position].add(msg)
                            mEveryCardMsgAdapterList[position].notifyItemInserted(mEveryCardMsgList[position].size - 1)
                            thisRV.scrollToPosition(mEveryCardMsgList[position].size - 1)
                            textReply.setText("")
                        }

                    }
                }
            }
        })
    }

    private fun addNewCards() {
        mMessageList.add(MessageCard("小军", "SRP", "10:12", "报告这周六交"))
        mMessageList.add(MessageCard("胖虎", "互联网+", "15:12", "木棉开会"))
        mMessageList.add(MessageCard("静香", "SRP", "9:12", "原型已经发给你了"))
        mEveryCardMsgList.add(ArrayList<Msg>())
        mEveryCardMsgList.add(ArrayList<Msg>())
        mEveryCardMsgList.add(ArrayList<Msg>())
        mEveryCardMsgAdapterList.add(MsgChatAdapter(mEveryCardMsgList[0]))
        mEveryCardMsgAdapterList.add(MsgChatAdapter(mEveryCardMsgList[1]))
        mEveryCardMsgAdapterList.add(MsgChatAdapter(mEveryCardMsgList[2]))
    }
}