package com.example.a_write



import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a_write.databinding.FragmentResetSendBinding


class ResetSendFragment : Fragment() {
    lateinit var binding : FragmentResetSendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetSendBinding.inflate(inflater, container, false)

        val button = binding.resetBackBt
        val drawable: Drawable? = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow)
        drawable?.setBounds(0, 0, 9.dpToPx(requireContext()), 16.dpToPx(requireContext())) // 크기 지정
        button.setCompoundDrawables(drawable, null, null, null)





        binding.resetCloseIv.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.resetBackBt.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }


        binding.resetEmailSendBt.setOnClickListener {
            //팝업 발생
            val popupFragment = PopupFragment()
            val bundle = Bundle().apply {
                putString(
                    "text",
                    "가입하신 이메일 주소로 비밀번호 재설정 메일을 전송했습니다. 비밀번호 재설정 메일의 링크를 클릭하면 새 비밀번호를 설정할 수 있습니다."
                )
            }
            popupFragment.arguments = bundle
            popupFragment.show(childFragmentManager, "popupFragment")
        }
        return binding.root
    }

    fun Int.dpToPx(context: Context): Int = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()


}
