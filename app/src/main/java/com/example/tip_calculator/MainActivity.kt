package com.example.tip_calculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tip_calculator.ui.theme.TIP_CALCULATORTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TIP_CALCULATORTheme {
                myapp()


            }
        }
    }
}


@Composable
fun TotalHeader(amount: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        color = colorResource(R.color.teal_700), shape = RoundedCornerShape(15.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Total Per Person",
                style = TextStyle(
                    color = Color.Black, fontSize = 20.sp, FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "$ $amount",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )


        }

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myapp() {
    val amount  = remember { mutableStateOf("") }
    val personcounter  = remember { mutableStateOf(1) }
    val tippercentage  = remember { mutableStateOf(0f) }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(topBar = {
            TopAppBar(title = {
                Text(
                    text = "Tip Calculator",
                    style = TextStyle(fontFamily = FontFamily.Serif),
                    fontSize = 35.sp, color = Color.Magenta,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center

                )
            })
        }) {
            Column(modifier = Modifier.padding(it)) {

                TotalHeader(getfloat(gettotal(amount.value,tippercentage.value,personcounter.value)))
                Spacer(modifier = Modifier.height(20.dp))
                Userinterface(
                    amount = amount.value, amountChange = {
                        amount.value = it
                    }, personcounter = personcounter.value, onAddOrReducePerson = {
                        if (it < 0) {
                            if (personcounter.value != 1) {
                                personcounter.value--
                            }
                        } else {
                            personcounter.value++
                        }
                    },
                    tippercentage.value , {
                        tippercentage.value = it
                    }

                )


            }

        }


    }
}



@Composable
fun Userinterface(
    amount: String,
    amountChange: (String) -> Unit,
    personcounter: Int,
    onAddOrReducePerson: (Int) -> Unit,
    tippercentage :Float,
    tipAmount : (Float) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 12.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = amount,
                onValueChange = {
                    amountChange.invoke(it)
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Enter Bill Amount")
                },
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })


            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Split", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.fillMaxWidth(.40f))
                CustomButton(imageVector = Icons.Default.KeyboardArrowDown, onClick = {
                    onAddOrReducePerson.invoke(1)

                })
                Spacer(modifier = Modifier.fillMaxWidth(.2f))
                Text(
                    text = "$personcounter",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                Spacer(modifier = Modifier.fillMaxWidth(.2f))
                CustomButton(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    onClick = {
                        onAddOrReducePerson.invoke(-1)

                    },

                    )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Tip", fontSize = 30.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.fillMaxWidth(.55f))
                Text(text = "$ ${getfloat(getdata(amount,tippercentage))}", fontSize = 30.sp, fontWeight = FontWeight.Bold)

            }
            Spacer(Modifier.height(10.dp))
            Text(text = "${getfloat(tippercentage.toString())} %", fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))

            Slider(value = tippercentage, onValueChange = {
                tipAmount.invoke(it)

            },  valueRange = 0f..100f,steps = 5)

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun system() {
    CustomButton(imageVector = Icons.Default.KeyboardArrowDown, onClick = {})


}

@Composable
fun CustomButton(imageVector: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .wrapContentSize()
            .padding(12.dp)
            .clickable {
                onClick.invoke()
            },
        shape = CircleShape
    ) {
        Icon(
            imageVector = imageVector, contentDescription = null, tint = Color.White,
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp)
        )
    }


}
fun getdata(tipamount :String ,tippersatage  : Float):String
{
  return when{
      tipamount.isEmpty()-> {
          "0"
      }

      else -> {
          val amount = tipamount.toFloat()
          (amount * tippersatage.div(100)).toString()}
  }

}
fun gettotal(amount: String,tippercentage: Float,personcounter: Int):String
{
    return when
    {
        amount.isEmpty()-> {
            "0"
        }else ->
        {
            val bill = amount.toFloat()
            val tip = bill * tippercentage.div(100)
            (bill + tip).div(personcounter).toString()

        }
    }
}
fun getfloat(str :String):String
{
   return if(str.isEmpty())
        ""
    else {
        val format = DecimalFormat("########.##")
        format.format(str.toFloat()).toString()
    }
}




