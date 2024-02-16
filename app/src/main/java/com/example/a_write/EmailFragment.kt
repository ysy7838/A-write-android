package com.example.a_write

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a_write.api.CodeService
import com.example.a_write.databinding.FragmentEmailBinding

class EmailFragment :Fragment(), CodeView{
    lateinit var binding : FragmentEmailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEmailBinding.inflate(inflater, container, false)


        binding.emailSendButtonTv.setOnClickListener{

            binding.emailExplain3Tv.visibility=View.VISIBLE
            binding.emailExplain4Tv.visibility=View.VISIBLE

            binding.emailSendButtonTv.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.grayA))


            val email = binding.emailNameEt.toString()
            if (!(email.contains('@') && email.indexOf('@') != email.lastIndex)) {
                //@뒤에 들어가야함
                Toast.makeText(activity, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            }
            else codeSend()

        }



        binding.emailLoginButtonTv.setOnClickListener{
            //goto login process
            val intent = Intent(activity,LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        return binding.root
    }




    private fun getUser(): User {
        val email: String = binding.emailNameEt.toString()
        val pwd = arguments?.getString("pwd") ?:""
        val name = arguments?.getString("name") ?:""

        return User(email, pwd, name)
    }

    private fun codeSend(){
        //코드전송 api
        val codeService = CodeService()
        codeService.setCodeView(this)

        codeService.sendCode(getUser().email)
    }

    override fun onEmailCodeSuccess() {
        Log.d("CodeSend", "Success")

        //실사용 땐 고치기
        Toast.makeText(activity, "code 전송 성공", Toast.LENGTH_SHORT).show()

        //이걸로 activity 데이터 전송
        (activity as? SignUpActivity)?.setUserData(getUser())

        //버튼 생성
        binding.codeNextBt.visibility=View.VISIBLE

        //버튼 clicker
        binding.codeNextBt.setOnClickListener{
            (context as SignUpActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.signup_frm, CodeFragment())
                .commit()
        }
    }

    override fun onEmailCodeFailure(message: String) {
        Log.d("CodeSend", "fail")
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}