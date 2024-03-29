package com.example.a_write

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.a_write.api.CodeService
import com.example.a_write.databinding.FragmentCodeBinding

class CodeFragment : Fragment() , CodeCheckView{
    lateinit var binding : FragmentCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeBinding.inflate(inflater, container, false)

        //pin number
        val editText1 = binding.number1
        val editText2 = binding.number2
        val editText3 = binding.number3
        val editText4 = binding.number4

        var flag = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    when {
                        editText1.text.hashCode() == s.hashCode() && s.length == 1 -> editText2.requestFocus()
                        editText2.text.hashCode() == s.hashCode() && s.length == 1 -> editText3.requestFocus()
                        editText3.text.hashCode() == s.hashCode() && s.length == 1 -> editText4.requestFocus()
                        editText4.text.hashCode() == s.hashCode() && s.length == 1 -> {
                            editText4.clearFocus()
                            val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            inputMethodManager.hideSoftInputFromWindow(editText4.windowToken, 0)
                            flag = true
                            binding.codeNextBt.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.red))
                        }
                        else -> {flag = false
                            binding.codeNextBt.setBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.grayA))
                        }
                    }
                }
            }
        }

        editText1.addTextChangedListener(textWatcher)
        editText2.addTextChangedListener(textWatcher)
        editText3.addTextChangedListener(textWatcher)
        editText4.addTextChangedListener(textWatcher)


        binding.codeNextBt.setOnClickListener{
            if(flag){

                val code:String =editText1.text.toString()+editText2.text.toString()+editText3.text.toString()+editText4.text.toString()

                //인증 코드 입력 api
                codeCheck(code)


            }
            else{
                Toast.makeText(activity, "잘못된 코드입니다.", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun codeCheck(code: String){

        val SignUpActivity = activity as? SignUpActivity
        val email = SignUpActivity?.user?.email

        val codeService = CodeService()
        codeService.setCodeCheckView(this)

        codeService.codeCheck(CodeCheck(email!!, code))
    }

    override fun onSuccess() {
        (context as SignUpActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.signup_frm, SignUpCompleteFragment())
            .commit()
    }

    override fun onFailure(message: String) {
        Log.d("CodeCheck", "fail")
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}