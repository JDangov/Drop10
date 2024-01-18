package com.drop.drop10

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.isDigitsOnly
import java.io.*
import kotlin.random.Random
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdSize
//import com.google.android.gms.ads.AdView
import androidx.compose.ui.platform.LocalClipboardManager

//AdMob Settings
//"ca-app-pub-6401010688303597~5674716391"
//const val csBannerUnitId = "ca-app-pub-3940256099942544/6300978111"
const val csBannerUnitId = "ca-app-pub-6401010688303597~5674716391"
//val coBannerAdSize = AdSize.BANNER
//const val ciAdBoundary = 10 //10 30
const val ciDefaultMaxUniverseSize = 10

//Light Theme
val cxGrey50 = Color(0xFFF8F9FA)
val cxGrey900 = Color(0xFF202124)
val cxGrey700 = Color(0xFF5F6368)
val cxGreen50 = Color(0xFFE6F4EA)
val cxGreen100 = Color(0xFFCEEAD6)

//Dark Theme
val cxWhite = Color(0xFFFFFFFF)
val cxGrey100 = Color(0xFFF1F3F4)
val cxCyan900 = Color(0xFF007B83)
val cxCyan700 = Color(0xFF129EAF)

val coDarkColorPalette = darkColors(background = cxCyan900, surface = cxCyan700, onSurface = cxWhite, primary = cxGrey900, onPrimary = cxWhite, secondary = cxGrey100)
val LightColorPalette = lightColors(background = cxGreen100, surface = cxGreen50, onSurface = cxGrey900, primary = cxGrey50, onPrimary = cxGrey900, secondary = cxGrey700)
val coAbrilFatface = FontFamily(Font(R.font.abril_fatface_regular))
val coMontserrat = FontFamily(Font(R.font.montserrat_regular), Font(R.font.montserrat_bold, FontWeight.Bold))
val coTypography = Typography(
    h1 = TextStyle(fontFamily = coAbrilFatface, fontWeight = FontWeight.Normal, fontSize = 30.sp),
    h2 = TextStyle(fontFamily = coMontserrat, fontWeight = FontWeight.Bold, fontSize = 20.sp),
    h3 = TextStyle(fontFamily = coMontserrat, fontWeight = FontWeight.Bold, fontSize = 14.sp),
    body1 = TextStyle(fontFamily = coMontserrat, fontWeight = FontWeight.Normal, fontSize = 14.sp)
)
val Shapes = Shapes(small = RoundedCornerShape(4.dp), medium = RoundedCornerShape(16.dp), large = RoundedCornerShape(0.dp))

const val csDefaultStateFileName = "state.txt"
const val csDefaultPatternFileName = "pattern.txt"
const val csDefaultPersistFileName = "persist.txt"
const val csMinString = "1"
var ciMinValue = csMinString.toInt()

const val csZeroString = "0"
const val csEmptyString = ""
const val csSpaceString = " "
const val csCommaString = ","
const val csDashString = "-"
const val csPipeString = "|"
const val csEOLNString = "\n"

const val csDoubleSpaceString = csSpaceString + csSpaceString
const val csDoubleEOLNString = csEOLNString + csEOLNString
const val csZeroCommaString = csZeroString + csCommaString
const val csCommaSpaceString = csCommaString + csSpaceString

const val ciEOLNCharacter = '\n'

const val csZeroLiteral = "@0@"
const val csOneLiteral = "@1@"
const val csTwoLiteral = "@2@"
const val csThreeLiteral = "@3@"
const val csFourLiteral = "@4@"
const val csFiveLiteral = "@5@"
const val csSixLiteral = "@6@"
const val csChosenLiteral = "@Chosen@"
const val csDrawnLiteral = "@Drawn@"
const val csIdLiteral = "@Id@"
const val csItemLiteral = "@Item@"
const val csMatchLiteral = "@Match@"
const val csMinLiteral = "@Min@"
const val csMaxLiteral = "@Max@"
const val csPickLiteral = "@Pick@"
const val csShownLiteral = "@Shown@"
const val csSizeLiteral = "@Size@"
const val csTicketLiteral = "@Ticket@"
const val csTicketsLiteral = "@Tickets@"
const val csValueLiteral = "@Value@"

const val csPatternFileName = "Pattern_" + csPickLiteral + "_" + csMatchLiteral + "_" + csSizeLiteral + ".csv"

const val csCalibrateStatusTemplate = "Range $csMinLiteral to $csMaxLiteral"
const val csEnterChosenListInstruction = "Enter Chosen list"
const val csEnterDrawnListInstruction = "Enter Drawn list"
const val csListSizeInstruction = "List Size"
const val csMaxDescription = "Max"
const val csTextString = "text"

const val csHomeDescription = "Home"
const val csExpandConfigurationDescription = "Expand Configuration"
const val csExpandConfigurationHelpDescription = "Expand Configuration Help"
const val csListSizeDescription = csListSizeInstruction + " <= " + csMaxLiteral
const val csExpandStateHelpDescription = "State Help"
const val csExpandSummaryDescription = "Expand Summary"
const val csFileDownloadDescription = "File Download"
const val csFileUploadDescription = "File Upload"
const val csCopyToClipboardDescription = "Copy To Clipboard"
const val csCopyFromClipboardDescription = "Copy From Clipboard"
const val csProcessHelpDescription = "Process Help"
const val csRandomizeDrawnNumbersDescription = "Check the tickets with a random draw"
const val csRandomizePermutationDescription = "Generate tickets with a randomized set"
const val csShowAllDescription = "Show All"

const val csCopiedToClipboard = "Tickets were copied" + csEOLNString + "to the clipboard"
const val csClipboardHeader = "Chosen: " + csChosenLiteral + csEOLNString + "Drawn: " + csDrawnLiteral + csDoubleEOLNString + "Tickets" + csEOLNString
const val csClipboardTicket = csIdLiteral + " (" + csMatchLiteral + "): " + csTicketLiteral + csEOLNString
const val cs5Header = "Tickets: " + csTicketsLiteral + csEOLNString + "Match:" + csFiveLiteral + "-" + csFourLiteral + "-" + csThreeLiteral + "-" + csTwoLiteral + "-" + csOneLiteral + "-" + csZeroLiteral
const val cs6Header = "Tickets: " + csTicketsLiteral + csEOLNString + "Match:" + csSixLiteral + "-" + csFiveLiteral + "-" + csFourLiteral + "-" + csThreeLiteral + "-" + csTwoLiteral + "-" + csOneLiteral + "-" + csZeroLiteral
const val csItemIsNotANumber = csItemLiteral + " is not a number"
const val csListSize = "List size of " + csSizeLiteral
const val csMatchText = "Match: " + csValueLiteral
const val csPickText = "Pick: " + csValueLiteral
const val csStateWasSaved = "The state was saved"
const val csShowingAllTickets = "Showing all tickets"
const val csShowingMatchedTickets = "Showing matched tickets"
const val csShownNumbersMaximum = csShownLiteral + " numbers are provided." + csEOLNString + "At most " + csMaxLiteral + " are required"
const val csShownNumbersMinimum = csShownLiteral + " numbers are provided." + csEOLNString + "At least " + csMinLiteral + " are required"
const val cs5Summary = "Tickets: " + csTicketsLiteral + csEOLNString + " Match 5: " + csFiveLiteral + csEOLNString + " Match 4: " + csFourLiteral + csEOLNString + " Match 3: " + csThreeLiteral + csEOLNString + " Match 2: " + csTwoLiteral + csEOLNString + " Match 1: " + csOneLiteral + csEOLNString + " Match 0: " + csZeroLiteral
const val cs6Summary = "Tickets: " + csTicketsLiteral + csEOLNString + " Match 6: " + csSixLiteral + csEOLNString + " Match 5: " + csFiveLiteral + csEOLNString + " Match 4: " + csFourLiteral + csEOLNString + " Match 3: " + csThreeLiteral + csEOLNString + " Match 2: " + csTwoLiteral + csEOLNString + " Match 1: " + csOneLiteral + csEOLNString + " Match 0: " + csZeroLiteral
const val csTicketsAreAvailable = csSizeLiteral + " tickets are available"

const val csSummaryHint = "Show the generated ticket summary"
const val csSaveSettingsHint = "Save the present settings"
const val csRetrieveSettingsHint = "Retrieve the saved settings"
const val csShowAllTicketsHint = "Show all tickets"
const val csCopyToClipboardHint = "Copy the tickets to the clipboard"
const val csCopyFromClipboardHint = "Copy the list from the clipboard"
const val csMaxRangeHint = ": Enter the max for the range of numbers"
const val csSetSizeHint = ": Enter how many numbers to generate"
const val csPickSizeHint = ": Choose how many numbers appear on a ticket"
const val csMatchSizeHint = ": Choose the minimum combination to match"

const val csKey1 = "HJEICFBGDA"
const val csKey2 = "MQLPSNTKRO"
const val csKey3 = "U"
const val csKey4 = "W"
const val csKey5 = "X"
const val csKey6 = "Z"

data class ReturnInfo(
    var sMessage: String,
    var bSucceeded: Boolean
)
data class TicketInfo(
    val sTicketId: String,
    var iMatchCount: Int,
    val lstItem: List<String>
)
data class ConfigurationsInfo (
    var sCaption: String,
    var sChosenNumbers: String,
    var sDrawnNumbers: String,
    var sMax: String,
    var sListSize: String,
    var bPickExtraSpaceFlag: Boolean,
    var sPickOptionsLabel: String,
    var lPickOptions: List<String>,
    var iPickSelectedOption: Int,
    var bMatchExtraSpaceFlag: Boolean,
    var sMatchOptionsLabel: String,
    var lMatchOptions: List<String>,
    var iMatchSelectedOption: Int,
    var sDirectoryName: String,
    var sStateFileName: String,
    var sPatternFileName: String,
    var lstTicket: List<TicketInfo>,
    var lstMatchedTicket: List<TicketInfo>,
    var lstTicketPattern: List<List<String>>,
    var sTicketPattern: String,
    var iMinUniverseSize: Int,
    var iMaxUniverseSize: Int,
    var bShowAllFlag: Boolean,
    var sPersistFileName: String,
    var sCode: String
)

var oConfigurationsInfo = ConfigurationsInfo (
    sCaption = csEmptyString, sChosenNumbers = csEmptyString, sDrawnNumbers = csEmptyString,
    bPickExtraSpaceFlag = true, sPickOptionsLabel = "Pick", lPickOptions = listOf("5", "6"), iPickSelectedOption = 0,
    bMatchExtraSpaceFlag = false, sMatchOptionsLabel ="Match", lMatchOptions = listOf("2", "3", "4"), iMatchSelectedOption = 0,
    sDirectoryName = csEmptyString, sMax = "99", sListSize = "9", iMinUniverseSize = 5, iMaxUniverseSize = ciDefaultMaxUniverseSize, bShowAllFlag = true,
    sStateFileName = csDefaultStateFileName, sPatternFileName = csDefaultPatternFileName,
    lstTicket = listOf(), lstMatchedTicket = listOf(), lstTicketPattern = listOf(), sTicketPattern = csEmptyString,
    sPersistFileName = csDefaultPersistFileName, sCode = ""
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Drop10App() {
    val modifier: Modifier = Modifier
    val oContext = LocalContext.current
    oConfigurationsInfo.sDirectoryName = oContext.filesDir.toString()
    convertStringToConfiguration(sFileName = oConfigurationsInfo.sStateFileName)

    var sCaption by remember { mutableStateOf(oConfigurationsInfo.sCaption) }
    var bExpandSummaryFlag by remember { mutableStateOf(false) }
    var bExpandStateHelpFlag by remember { mutableStateOf(false) }
    var bExpandProcessHelpFlag by remember { mutableStateOf(false) }
    var bExpandConfigurationHelpFlag by remember { mutableStateOf(false) }
    var bExpandConfigurationFlag by remember { mutableStateOf(false) }
    var bExpandSummary by remember { mutableStateOf(false) }
    var sChosenNumbers by remember { mutableStateOf(oConfigurationsInfo.sChosenNumbers) }
    var sDrawnNumbers by remember { mutableStateOf(oConfigurationsInfo.sDrawnNumbers) }
    var sMax by remember { mutableStateOf(oConfigurationsInfo.sMax) }
    var sListSize by remember { mutableStateOf(oConfigurationsInfo.sListSize) }
    var iPickSelectedOption by remember { mutableStateOf(oConfigurationsInfo.iPickSelectedOption) }
    var iMatchSelectedOption by remember { mutableStateOf(oConfigurationsInfo.iMatchSelectedOption) }
    var lstTicket by remember { mutableStateOf(oConfigurationsInfo.lstTicket) }
    val clipboard = oContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipboardManager = LocalClipboardManager.current
    var bShowAllFlag by remember { mutableStateOf(oConfigurationsInfo.bShowAllFlag) }
    var oDowloadBorderColor by remember {mutableStateOf(Color.Transparent)}
    var oUploadBorderColor by remember {mutableStateOf(Color.Transparent)}
    var oCopyToClipboardBorderColor by remember {mutableStateOf(Color.Transparent)}
    var oCopyToChosenNumbersBorderColor by remember {mutableStateOf(Color.Transparent)}
    var oCalibrateListBorderColor by remember {mutableStateOf(Color.Transparent)}
    var oCopyToDrawnNumbersBorderColor by remember {mutableStateOf(Color.Transparent)}
    var oVerifyListBorderColor by remember {mutableStateOf(Color.Transparent)}
    var oShowMatchedBorderColor by remember {mutableStateOf(Color.Transparent)}

    fun clearClickedBorders() {
        oDowloadBorderColor = Color.Transparent
        oUploadBorderColor = Color.Transparent
        oCopyToClipboardBorderColor = Color.Transparent
        oCopyToChosenNumbersBorderColor = Color.Transparent
        oCalibrateListBorderColor = Color.Transparent
        oCopyToDrawnNumbersBorderColor = Color.Transparent
        oVerifyListBorderColor = Color.Transparent
        oShowMatchedBorderColor = Color.Transparent
    }

    val rawResource = oContext.resources.openRawResource(R.raw.code_).bufferedReader().use { it.readText() }
    oConfigurationsInfo.sCode = rememberUpdatedState(rawResource).value

    sCaption = refresh().sMessage
    bShowAllFlag = oConfigurationsInfo.bShowAllFlag
    sCaption = checkDraw().sMessage
    lstTicket = getMatchedTicketList()

    Scaffold(topBar = {
        Column(modifier = Modifier.animateContentSize(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow))) {
            Row(modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colors.primary).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Image(modifier = modifier.size(56.dp).padding(8.dp).clickable {
                        sCaption = if (bExpandSummary) headerMatch() else summarizeMatch()
                        bExpandSummary = !bExpandSummary
                        clearClickedBorders()
                    },
                    painter = painterResource(R.mipmap.ic_launcher_foreground), contentDescription = csHomeDescription
                )
                Text(text = sCaption, style = coTypography.h2)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { bExpandSummaryFlag = !bExpandSummaryFlag; clearClickedBorders() }) {
                    Icon(imageVector = if (bExpandSummaryFlag) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        tint = MaterialTheme.colors.secondary, contentDescription = csExpandSummaryDescription
                    )
                }
            }
//            if (oConfigurationsInfo.iMaxUniverseSize <= ciAdBoundary)
//                Row(modifier = modifier.fillMaxWidth().background( color = MaterialTheme.colors.primary).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                    loadAd(csBannerUnitId, coBannerAdSize, oContext)
//                }
            if (bExpandSummaryFlag) {
                Row(modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colors.primary), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp).clickable {
                                saveTextToDisk(sFileName = oConfigurationsInfo.sPersistFileName, sText = convertConfigurationToString())
                                sCaption = csStateWasSaved
                                clearClickedBorders()
                                oDowloadBorderColor = Color.Black
                            }.border(BorderStroke(1.dp, oDowloadBorderColor)),
                        painter = painterResource(R.drawable.file_download_24dp), contentDescription = csFileDownloadDescription
                    )
                    Image(modifier = modifier.size(48.dp).padding(1.dp)
                            .clickable {
                                convertStringToConfiguration(sFileName = oConfigurationsInfo.sPersistFileName)
                                iMatchSelectedOption = oConfigurationsInfo.iMatchSelectedOption
                                iPickSelectedOption = oConfigurationsInfo.iPickSelectedOption
                                sChosenNumbers = oConfigurationsInfo.sChosenNumbers
                                sDrawnNumbers = oConfigurationsInfo.sDrawnNumbers
                                sListSize = oConfigurationsInfo.sListSize
                                sMax = oConfigurationsInfo.sMax
                                bShowAllFlag = oConfigurationsInfo.bShowAllFlag
                                convertConfigurationToString()
                                sCaption = checkDraw().sMessage
                                lstTicket = getMatchedTicketList()
                                clearClickedBorders()
                                oUploadBorderColor = Color.Black
                            }.border(BorderStroke(1.dp, oUploadBorderColor)),
                        painter = painterResource(R.drawable.file_upload_24dp), contentDescription = csFileUploadDescription
                    )
                    Image(modifier = modifier.size(48.dp).padding(1.dp).clickable {
                                clipboard.setPrimaryClip(ClipData.newPlainText(csTextString, getCopyString(lstTicket)))
                                sCaption = csCopiedToClipboard
                                clearClickedBorders()
                                oCopyToClipboardBorderColor = Color.Black
                            }.border(BorderStroke(1.dp, oCopyToClipboardBorderColor)),
                        painter = painterResource(R.drawable.copy_to_clipboard_24dp), contentDescription = csCopyToClipboardDescription
                    )
                    if (bExpandStateHelpFlag) {
                        IconButton(onClick = { bExpandStateHelpFlag = !bExpandStateHelpFlag; clearClickedBorders() }) {
                            Icon(imageVector = Icons.Filled.ExpandLess, tint = MaterialTheme.colors.secondary, contentDescription = csExpandConfigurationDescription)
                            clearClickedBorders()
                        }
                    } else {
                        Image(
                            modifier = modifier.size(48.dp).padding(1.dp).clickable { bExpandStateHelpFlag = !bExpandStateHelpFlag; clearClickedBorders() },
                            painter = painterResource(R.drawable.help_24dp),contentDescription = csExpandStateHelpDescription
                        )
                    }
                }
//                if (oConfigurationsInfo.iMaxUniverseSize <= ciAdBoundary)
//                    Row(modifier = modifier.fillMaxWidth().background( color = MaterialTheme.colors.primary).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                        loadAd(csBannerUnitId, coBannerAdSize, oContext)
//                    }
            } else bExpandStateHelpFlag = false
            if (bExpandStateHelpFlag) {
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.file_download_24dp), contentDescription = csFileDownloadDescription)
                    Text(text = csSaveSettingsHint, style = coTypography.body1)
                }
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.file_upload_24dp), contentDescription = csFileUploadDescription)
                    Text(text = csRetrieveSettingsHint, style = coTypography.body1)
                }
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.copy_to_clipboard_24dp), contentDescription = csCopyToClipboardDescription)
                    Text(text = csCopyToClipboardHint, style = coTypography.body1)
                }
            }
            Row(modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colors.primary), verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(10.dp))
                TextField(onValueChange = {
                        sChosenNumbers = it
                        oConfigurationsInfo.sChosenNumbers = it
                        sCaption = checkDraw().sMessage
                        lstTicket = getMatchedTicketList()
                    },
                    label = { Text(csEnterChosenListInstruction, style = coTypography.h3) },
                    modifier = Modifier.width(220.dp), value = sChosenNumbers, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(Modifier.width(1.dp))
                Image(modifier = modifier.size(48.dp).padding(1.dp).clickable {
                            val clipboardText = clipboardManager.getText()
                            if (clipboardText != null) {
                                sChosenNumbers = clipboardText.toString()
                                oConfigurationsInfo.sChosenNumbers = sChosenNumbers
                                sCaption = checkDraw().sMessage
                                lstTicket = getMatchedTicketList()
                            }
                            clearClickedBorders()
                            oCopyToChosenNumbersBorderColor = Color.Black
                        }.border(BorderStroke(1.dp, oCopyToChosenNumbersBorderColor)),
                    painter = painterResource(R.drawable.copy_from_clipboard_24dp), contentDescription = csCopyToClipboardDescription
                )
                Spacer(Modifier.width(1.dp))
                    Image(modifier = modifier.size(48.dp).padding(1.dp).clickable {
                            sCaption = randomPermute().sMessage
                            sChosenNumbers = oConfigurationsInfo.sChosenNumbers
                            lstTicket = getMatchedTicketList()
                            clearClickedBorders()
                            oCalibrateListBorderColor = Color.Black
                        }.border(BorderStroke(1.dp, oCalibrateListBorderColor)),
                    painter = painterResource(R.drawable.calibrate_list_24dp), contentDescription = csRandomizePermutationDescription
                )
                if (bExpandProcessHelpFlag) {
                    IconButton(onClick = { bExpandProcessHelpFlag = !bExpandProcessHelpFlag; clearClickedBorders() }) {
                        Icon(imageVector = Icons.Filled.ExpandLess, tint = MaterialTheme.colors.secondary, contentDescription = csExpandConfigurationDescription)
                    }
                } else {
                    Image(modifier = modifier.size(48.dp).padding(1.dp).clickable { bExpandProcessHelpFlag = !bExpandProcessHelpFlag; clearClickedBorders() },
                        painter = painterResource(R.drawable.help_24dp),contentDescription = csProcessHelpDescription
                    )
                }
            }
            Row(modifier = modifier.fillMaxWidth()
                .background(color = MaterialTheme.colors.primary), verticalAlignment = Alignment.CenterVertically) {
                Spacer(Modifier.width(10.dp))
                TextField(onValueChange = {
                        oConfigurationsInfo.sDrawnNumbers = it
                        sCaption = checkDraw().sMessage
                        lstTicket = getMatchedTicketList()
                    },
                    label = { Text(csEnterDrawnListInstruction, style = coTypography.h3) },
                    modifier = Modifier.width(150.dp), value = sDrawnNumbers, singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(Modifier.width(1.dp))
                Image(modifier = modifier.size(48.dp).padding(1.dp).clickable {
                            val clipboardText = clipboardManager.getText()
                            if (clipboardText != null) {
                                sDrawnNumbers = clipboardText.toString()
                                oConfigurationsInfo.sDrawnNumbers = sDrawnNumbers
                                sCaption = checkDraw().sMessage
                                lstTicket = getMatchedTicketList()
                            }
                        clearClickedBorders()
                        oCopyToDrawnNumbersBorderColor = Color.Black
                    }.border(BorderStroke(1.dp, oCopyToDrawnNumbersBorderColor)),
                    painter = painterResource(R.drawable.copy_from_clipboard_24dp), contentDescription = csCopyToClipboardDescription
                )
                Spacer(Modifier.width(1.dp))
                Image(modifier = modifier.size(48.dp).padding(1.dp).clickable {
                            @Suppress("NAME_SHADOWING")
                            val oReturnInfo = permute()
                            if (oReturnInfo.bSucceeded) {
                                sCaption = randomDraw().sMessage
                                sDrawnNumbers = oConfigurationsInfo.sDrawnNumbers
                                sChosenNumbers = oConfigurationsInfo.sChosenNumbers
                                lstTicket = getMatchedTicketList()
                            } else sCaption = oReturnInfo.sMessage
                            clearClickedBorders()
                            oVerifyListBorderColor = Color.Black
                    }.border(BorderStroke(1.dp, oVerifyListBorderColor)),
                    painter = painterResource(R.drawable.verify_list_24dp), contentDescription = csRandomizeDrawnNumbersDescription
                )
                Image(modifier = modifier.size(48.dp).padding(1.dp).clickable {
                            oConfigurationsInfo.bShowAllFlag = !oConfigurationsInfo.bShowAllFlag
                            lstTicket = getMatchedTicketList()
                            bShowAllFlag = oConfigurationsInfo.bShowAllFlag
                            sCaption = (if (oConfigurationsInfo.bShowAllFlag) csShowingAllTickets else csShowingMatchedTickets)
                            clearClickedBorders()
                            oShowMatchedBorderColor = Color.Black
                        }.border(BorderStroke(1.dp, oShowMatchedBorderColor)),
                    painter = painterResource(if (bShowAllFlag) R.drawable.show_matched_only_24dp else R.drawable.show_all_24dp),
                    contentDescription = csShowAllDescription
                )
                Spacer(Modifier.width(1.dp))
                IconButton(onClick = { bExpandConfigurationFlag = !bExpandConfigurationFlag; clearClickedBorders() }) {
                    Icon(imageVector = if (bExpandConfigurationFlag) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        tint = MaterialTheme.colors.secondary, contentDescription = csExpandConfigurationDescription
                    )
                }
            }
            if (bExpandProcessHelpFlag) {
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.width(10.dp))
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.mipmap.ic_launcher_foreground), contentDescription = csHomeDescription)
                    Text(text = csSummaryHint, style = coTypography.body1)
                }
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.calibrate_list_24dp), contentDescription = csRandomizePermutationDescription)
                    Text(text = csRandomizePermutationDescription, style = coTypography.body1)
                }
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.verify_list_24dp), contentDescription = csRandomizeDrawnNumbersDescription)
                    Text(text = csRandomizeDrawnNumbersDescription, style = coTypography.body1)
                }
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.show_matched_only_24dp), contentDescription = csShowAllDescription)
                    Text(text = "Show only the matched tickets", style = coTypography.body1)
                }
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.show_all_24dp), contentDescription = csShowAllDescription)
                    Text(text = csShowAllTicketsHint, style = coTypography.body1)
                }
                Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray), verticalAlignment = Alignment.CenterVertically) {
                    Image(modifier = modifier.size(48.dp).padding(1.dp), painter = painterResource(R.drawable.copy_to_clipboard_24dp), contentDescription = csCopyFromClipboardDescription)
                    Text(text = csCopyFromClipboardHint, style = coTypography.body1)
                }
            }
            if (bExpandConfigurationFlag) {
                Row(modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colors.primary).padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.width(20.dp))
                    TextField(onValueChange = {
                        sMax = it
                        oConfigurationsInfo.sMax = sMax
                        sCaption = csCalibrateStatusTemplate.replace(csMinLiteral, csMinString).replace(csMaxLiteral, sMax)
                    },
                        label = { Text(csMaxDescription, style = coTypography.h3) },
                        modifier = Modifier.width(70.dp), value = sMax, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(Modifier.width(10.dp))
                    TextField(onValueChange = {
                            sListSize = it
                            sCaption = csListSize.replace(csSizeLiteral, sListSize)
                            oConfigurationsInfo.sListSize = sListSize
                        },
                        label = { Text(csListSizeDescription.replace(csMaxLiteral, oConfigurationsInfo.iMaxUniverseSize.toString()), style = coTypography.h3) },
                        modifier = Modifier.width(140.dp), value = sListSize, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    if (bExpandConfigurationHelpFlag) {
                        IconButton(onClick = { bExpandConfigurationHelpFlag = !bExpandConfigurationHelpFlag; clearClickedBorders() }) {
                            Icon(imageVector = Icons.Filled.ExpandLess, tint = MaterialTheme.colors.secondary, contentDescription = csExpandConfigurationHelpDescription)
                        }
                    } else { Image(modifier = modifier.size(48.dp).padding(1.dp).clickable { bExpandConfigurationHelpFlag = !bExpandConfigurationHelpFlag; clearClickedBorders() },
                            painter = painterResource(R.drawable.help_24dp), contentDescription = csExpandConfigurationDescription
                        )
                    }
                }
//                if (oConfigurationsInfo.iMaxUniverseSize <= ciAdBoundary)
//                    Row(modifier = modifier.fillMaxWidth().background( color = MaterialTheme.colors.primary).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
//                        loadAd(csBannerUnitId, coBannerAdSize, oContext)
//                }
                if (bExpandConfigurationHelpFlag) {
                    Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray).padding(bottom=10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(Modifier.width(10.dp))
                        Text(text = csMaxDescription, style = coTypography.h2)
                        Text(text = csMaxRangeHint, style = coTypography.body1)
                    }
                    Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray).padding(bottom=10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(Modifier.width(10.dp))
                        Text(text = csListSizeInstruction, style = coTypography.h2)
                        Text(text = csSetSizeHint, style = coTypography.body1)
                    }
                    Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray).padding(bottom=10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(Modifier.width(10.dp))
                        Text(text = oConfigurationsInfo.sPickOptionsLabel, style = coTypography.h2)
                        Text(text = csPickSizeHint, style = coTypography.body1)
                    }
                    Row(modifier = modifier.fillMaxWidth().background(color = Color.LightGray).padding(bottom=10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Spacer(Modifier.width(10.dp))
                        Text(text = oConfigurationsInfo.sMatchOptionsLabel, style = coTypography.h2)
                        Text(text = csMatchSizeHint, style = coTypography.body1)
                    }
                }
                Row(modifier = modifier.fillMaxWidth().background(color = MaterialTheme.colors.primary)
                        .padding(bottom = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.width(45.dp))
                    Card(elevation = 4.dp, modifier = modifier.padding(1.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(oConfigurationsInfo.sPickOptionsLabel, style = coTypography.h2)
                            oConfigurationsInfo.lPickOptions.forEachIndexed { index, text ->
                                Row(modifier = Modifier.padding(vertical = 1.dp), verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(onClick = {
                                            iPickSelectedOption = index
                                            oConfigurationsInfo.iPickSelectedOption = index
                                        },
                                        selected = (iPickSelectedOption == index), modifier = Modifier.padding(0.dp)
                                    )
                                    Text(text = text, modifier = Modifier.padding(start = 8.dp), style = coTypography.h3)
                                    sCaption = checkDraw().sMessage
                                    lstTicket = getMatchedTicketList()
                                }
                            }
                            Spacer(Modifier.height(10.dp))
                            if (oConfigurationsInfo.bPickExtraSpaceFlag) { Spacer(Modifier.height(50.dp)) }
                            Text(style = coTypography.h3, text = csPickText.replace(csValueLiteral, oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption]),
                            )
                        }
                    }
                    Spacer(Modifier.width(45.dp))
                    Card(elevation = 4.dp, modifier = modifier.padding(1.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(oConfigurationsInfo.sMatchOptionsLabel, style = coTypography.h2)
                            oConfigurationsInfo.lMatchOptions.forEachIndexed { index, text ->
                                Row(modifier = Modifier.padding(vertical = 1.dp), verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(onClick = {
                                            iMatchSelectedOption = index
                                            oConfigurationsInfo.iMatchSelectedOption = index
                                        },
                                        selected = (iMatchSelectedOption == index), modifier = Modifier.padding(0.dp)
                                    )
                                    Text(text = text, modifier = Modifier.padding(start = 8.dp), style = coTypography.h3)
                                    sCaption = checkDraw().sMessage
                                    lstTicket = getMatchedTicketList()
                                }
                            }
                            Spacer(Modifier.height(10.dp))
                            if (oConfigurationsInfo.bMatchExtraSpaceFlag) { Spacer(Modifier.height(50.dp)) }
                            Text(style = coTypography.h3,
                                text = csMatchText.replace(csValueLiteral, oConfigurationsInfo.lMatchOptions[oConfigurationsInfo.iMatchSelectedOption])
                            )
                        }
                    }
                }
            } else bExpandConfigurationHelpFlag = false
        }
    }) {
        LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
            items(lstTicket) {
                val stTicket: TicketInfo = it
                val iMatchCount = oConfigurationsInfo.lMatchOptions[oConfigurationsInfo.iMatchSelectedOption].toInt()
                val saDrawnNumber = sDrawnNumbers.split(csSpaceString)
                Card(elevation = 4.dp, modifier = modifier.padding(1.dp)) {
                    Column(modifier = modifier.padding(start = 1.dp, top = 1.dp, bottom = 1.dp, end = 1.dp)) {
                        Row(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                            Spacer(Modifier.width(10.dp))
                            Text(text = stTicket.sTicketId, color = if (stTicket.iMatchCount >= iMatchCount) Color.Red else Color.Black,  style = coTypography.h2,
                                modifier = Modifier
                                    .border(border = BorderStroke(3.dp, if (stTicket.iMatchCount >= iMatchCount) Color.Red else Color.Blue), shape = CircleShape)
                                    .background(Color.Transparent)
                                    .padding(8.dp))
                            Spacer(Modifier.width(30.dp))
                            for (sItem in stTicket.lstItem) {
                                Text(text = sItem, color = if (sItem in saDrawnNumber) Color.Red else Color.Black, style = coTypography.h2, modifier = Modifier
                                    .border(width = 4.dp, shape = CutCornerShape(8.dp), brush = Brush.horizontalGradient(
                                            colors = if (sItem in saDrawnNumber) listOf(Color.Red, Color.Blue, Color.Green) else listOf(Color.Blue, Color.Red, Color.Green),
                                            startX = 0.0f, endX = 500.0f, tileMode = TileMode.Repeated
                                        )
                                    )
                                    .background(Color.Transparent)
                                    .padding(8.dp))
                                Spacer(Modifier.width(8.dp))
                            }
                            Spacer(Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

//@SuppressLint("ComposableNaming")
//@Composable
//fun loadAd(adUnitId: String, adSize: AdSize, oContext: Context) {
//    AndroidView(modifier = Modifier.fillMaxWidth(),
//        factory = {
//            val adView = AdView(oContext)
//            adView.adUnitId = adUnitId
//            adView.setAdSize(adSize)
//            adView.loadAd(AdRequest.Builder().build())
//            adView
//        }
//    )
//}

@Composable
fun Drop10Theme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) { coDarkColorPalette } else { LightColorPalette }
    MaterialTheme(colors = colors, typography = coTypography, shapes = Shapes, content = content)
}

@Preview
@Composable
fun Drop10Preview() { Drop10Theme(darkTheme = false) { Drop10App() } }

@Preview
@Composable
fun Drop10DarkThemePreview() { Drop10Theme(darkTheme = true) { Drop10App() } }
fun saveTextToDisk(sFileName: String, sText: String): String {
    try { val file = File(oConfigurationsInfo.sDirectoryName, sFileName); file.writeText(sText) }
    catch (e: Exception) { return csEmptyString }
    return sFileName
}

fun readTextFromDisk(sFileName: String): String {
    var sText: String
    try { val file = File(oConfigurationsInfo.sDirectoryName, sFileName); sText = file.readText()}
    catch (e: Exception) { sText = csEmptyString }
    return sText
}

fun getCleanSet(sSet: String): String {
    return sSet
        .replace(csCommaString, csSpaceString).replace(".", csSpaceString).replace(csDashString, csSpaceString)
        .replace(csDoubleSpaceString, csSpaceString).replace(csDoubleSpaceString, csSpaceString).replace(csDoubleSpaceString, csSpaceString)
        .trim()
}

fun convertConfigurationToString(): String{
    return (
        oConfigurationsInfo.bMatchExtraSpaceFlag.toString() + csPipeString +
        oConfigurationsInfo.bPickExtraSpaceFlag.toString() + csPipeString +
        oConfigurationsInfo.iMatchSelectedOption.toString() + csPipeString +
        oConfigurationsInfo.iPickSelectedOption.toString() + csPipeString +
        oConfigurationsInfo.sCaption + csPipeString +
        oConfigurationsInfo.sChosenNumbers + csPipeString +
        oConfigurationsInfo.sDirectoryName + csPipeString +
        oConfigurationsInfo.sDrawnNumbers + csPipeString +
        oConfigurationsInfo.sListSize + csPipeString +
        oConfigurationsInfo.sMatchOptionsLabel + csPipeString +
        oConfigurationsInfo.sMax + csPipeString +
        oConfigurationsInfo.sPatternFileName + csPipeString +
        oConfigurationsInfo.sPickOptionsLabel + csPipeString +
        oConfigurationsInfo.sStateFileName + csPipeString +
        oConfigurationsInfo.sTicketPattern + csPipeString +
        oConfigurationsInfo.bShowAllFlag.toString() + csPipeString +
        oConfigurationsInfo.iMinUniverseSize.toString() + csPipeString +
        oConfigurationsInfo.iMaxUniverseSize.toString() + csPipeString
    )
}

fun convertStringToConfiguration(sFileName: String) {
    val sConfiguration = readTextFromDisk(sFileName = sFileName)
    if (sConfiguration != csEmptyString) {
    val saConfiguration = sConfiguration.split(csPipeString)
        oConfigurationsInfo.bMatchExtraSpaceFlag = saConfiguration[0].toBoolean()
        oConfigurationsInfo.bPickExtraSpaceFlag = saConfiguration[1].toBoolean()
        oConfigurationsInfo.iMatchSelectedOption = saConfiguration[2].toInt()
        oConfigurationsInfo.iPickSelectedOption = saConfiguration[3].toInt()
        oConfigurationsInfo.sCaption =  saConfiguration[4]
        oConfigurationsInfo.sChosenNumbers = saConfiguration[5]
        oConfigurationsInfo.sDirectoryName = saConfiguration[6]
        oConfigurationsInfo.sDrawnNumbers = saConfiguration[7]
        oConfigurationsInfo.sListSize = saConfiguration[8]
        oConfigurationsInfo.sMatchOptionsLabel = saConfiguration[9]
        oConfigurationsInfo.sMax = saConfiguration[10]
        oConfigurationsInfo.sPatternFileName = saConfiguration[11]
        oConfigurationsInfo.sPickOptionsLabel = saConfiguration[12]
        oConfigurationsInfo.sStateFileName = saConfiguration[13]
        oConfigurationsInfo.sTicketPattern = saConfiguration[14]
        oConfigurationsInfo.bShowAllFlag = saConfiguration[15].toBoolean()
        oConfigurationsInfo.iMinUniverseSize = saConfiguration[16].toInt()
        oConfigurationsInfo.iMaxUniverseSize = saConfiguration[17].toInt()
    }
}

fun getPatternListFromString(sTicketPattern: String): List<List<String>> {
    val slPattern = sTicketPattern.split(csEOLNString)
    val slFileDetails = slPattern[0].split(csCommaString)
    val iReadPickValue = slFileDetails[0].toInt()
    val lstTicketPattern = mutableListOf<List<String>>()
    val iPatternLength: Int = slPattern.size
    lstTicketPattern.clear()
    for (iIndex in 1 until iPatternLength) {
        var slTicketPattern = slPattern[iIndex].split(csCommaString)
        if (slTicketPattern.size < iReadPickValue) { slTicketPattern = (csZeroCommaString + slPattern[iIndex]).split(csCommaString) }
        lstTicketPattern.add(slTicketPattern)
    }
    return lstTicketPattern
}

fun refreshPatternFiles(sConfigurationPickValue: String, sConfigurationMatchValue: String, sConfigurationListSize: String) {
    var sPattern = oConfigurationsInfo.sCode.replace(csKey6[0], '3').replace(csKey5[0], '2').replace(csKey4[0], '1').replace(csKey3[0], ciEOLNCharacter)
    for (iIndex in 0 until 10) { sPattern = sPattern.replace(csKey2[iIndex].toString(), iIndex.toString() + csEOLNString) }
    for (iIndex in 0 until 10) { sPattern = sPattern.replace(csKey1[iIndex].toString(), iIndex.toString()+ csCommaString) }
    saveTextToDisk(sText = sPattern, sFileName = csDefaultPatternFileName)
    var iMinUniverseSize = oConfigurationsInfo.iMinUniverseSize
    var iMaxUniverseSize = oConfigurationsInfo.iMaxUniverseSize
    for (sTicketPatternSet in sPattern.split(csDoubleEOLNString)) {
        val slPattern = sTicketPatternSet.split(csEOLNString)
        val slFileDetails = slPattern[0].split(csCommaString)
        val sReadPickValue = slFileDetails[0]
        val sReadMatchValue = slFileDetails[1]
        val sReadSizeValue = slFileDetails[2]
        val iReadSizeValue = sReadSizeValue.toInt()
        if (iReadSizeValue < iMinUniverseSize) { iMinUniverseSize = iReadSizeValue }
        if (iReadSizeValue > iMaxUniverseSize) { iMaxUniverseSize = iReadSizeValue }
        saveTextToDisk(sText = sTicketPatternSet,
            sFileName = csPatternFileName.replace(csPickLiteral, sReadPickValue).replace(
                csMatchLiteral, sReadMatchValue).replace(csSizeLiteral, sReadSizeValue)
        )
        if ((sConfigurationPickValue == sReadPickValue) and (sConfigurationMatchValue == sReadMatchValue) and (sConfigurationListSize == sReadSizeValue)) {
            oConfigurationsInfo.sTicketPattern = sTicketPatternSet
            val iReadPickValue = sReadPickValue.toInt()
            val lstNewTicketPattern = mutableListOf<List<String>>()
            val iPatternLength: Int = slPattern.size
            lstNewTicketPattern.clear()
            for (iIndex in 1 until iPatternLength) {
                var slTicketPattern = slPattern[iIndex].split(csCommaString)
                if (slTicketPattern.size < iReadPickValue) { slTicketPattern = (csZeroCommaString + slPattern[iIndex]).split(csCommaString) }
                lstNewTicketPattern.add(slTicketPattern)
            }
            oConfigurationsInfo.lstTicketPattern = lstNewTicketPattern
        }
    }
    oConfigurationsInfo.iMinUniverseSize = iMinUniverseSize
    oConfigurationsInfo.iMaxUniverseSize = iMaxUniverseSize
    saveTextToDisk(sFileName = oConfigurationsInfo.sStateFileName, sText = convertConfigurationToString())
}

fun permute(): ReturnInfo {
    val oReturnInfo = ReturnInfo(csEmptyString, false)
    oConfigurationsInfo.sChosenNumbers = getCleanSet(oConfigurationsInfo.sChosenNumbers)
    val saChosenNumbers = oConfigurationsInfo.sChosenNumbers.split(csSpaceString)
    val iChosenNumbersSize = saChosenNumbers.size
    val iPickSize = oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption].toInt()
    if ((oConfigurationsInfo.sChosenNumbers + oConfigurationsInfo.sDrawnNumbers) == csEmptyString) {
        oReturnInfo.sMessage = headerMatch()
        return oReturnInfo
    }
    if (iChosenNumbersSize < iPickSize) {
        oReturnInfo.sMessage = csShownNumbersMinimum.replace(csShownLiteral, iChosenNumbersSize.toString()).replace(csMinLiteral, iPickSize.toString())
        return oReturnInfo
    }
    if (iChosenNumbersSize < oConfigurationsInfo.iMinUniverseSize) {
        oReturnInfo.sMessage = csShownNumbersMinimum.replace(csShownLiteral, iChosenNumbersSize.toString())
            .replace(csMinLiteral, oConfigurationsInfo.iMinUniverseSize.toString())
        return oReturnInfo
    }
    if (iChosenNumbersSize > oConfigurationsInfo.iMaxUniverseSize) {
        oReturnInfo.sMessage = csShownNumbersMaximum.replace(csShownLiteral, iChosenNumbersSize.toString())
            .replace(csMaxLiteral, oConfigurationsInfo.iMaxUniverseSize.toString())
        return oReturnInfo
    }
    for (iIndex in 0 until iChosenNumbersSize) {
        val sChosen = saChosenNumbers[iIndex]
        if (!sChosen.isDigitsOnly()) {
            oReturnInfo.sMessage = csItemIsNotANumber.replace(csItemLiteral, sChosen)
            return oReturnInfo
        }
    }
    val sConfigurationPickValue = oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption]
    val sConfigurationMatchValue = oConfigurationsInfo.lMatchOptions[oConfigurationsInfo.iMatchSelectedOption]
    val sConfigurationListSize = iChosenNumbersSize.toString()
    oConfigurationsInfo.sTicketPattern = readTextFromDisk(sFileName = csPatternFileName
        .replace(csPickLiteral, sConfigurationPickValue).replace(csMatchLiteral, sConfigurationMatchValue).replace(
            csSizeLiteral, sConfigurationListSize)
    )
    if (oConfigurationsInfo.sTicketPattern == csEmptyString) {
        refreshPatternFiles(
            sConfigurationPickValue = sConfigurationPickValue,
            sConfigurationMatchValue = sConfigurationMatchValue,
            sConfigurationListSize = sConfigurationListSize)
    }
    oConfigurationsInfo.lstTicketPattern = getPatternListFromString(sTicketPattern = oConfigurationsInfo.sTicketPattern)
    val iListSize = oConfigurationsInfo.lstTicketPattern.size

    val iTicketPatternSize = oConfigurationsInfo.lstTicketPattern[0].size
    val lstNewTicket = mutableListOf<TicketInfo>()
    for (iIndex in 0 until iListSize) {
        val lstTicketPattern = oConfigurationsInfo.lstTicketPattern[iIndex]
        val lstNewPattern = mutableListOf<String>()
        for (iPatternIndex in 0 until iTicketPatternSize) { lstNewPattern.add(saChosenNumbers[lstTicketPattern[iPatternIndex].toInt()]) }
        lstNewTicket.add(TicketInfo(sTicketId = (iIndex + 1).toString(), iMatchCount = 0, lstItem = lstNewPattern.toList()))
    }
    oConfigurationsInfo.lstTicket = lstNewTicket
    oReturnInfo.bSucceeded = true
    oReturnInfo.sMessage = csTicketsAreAvailable.replace(csSizeLiteral, lstNewTicket.size.toString())

    saveTextToDisk(sFileName = oConfigurationsInfo.sStateFileName, sText = convertConfigurationToString())
    return oReturnInfo
}

fun randomPermute(): ReturnInfo {
    val oReturnInfo = ReturnInfo(csEmptyString, false)
    val iMinBoundary = ciMinValue
    val iMaxBoundary = oConfigurationsInfo.sMax.toInt()
    val iListSize = oConfigurationsInfo.sListSize.toInt()
    if (iMinBoundary > iListSize) {
        oReturnInfo.sMessage = csShownNumbersMinimum.replace(csShownLiteral, iListSize.toString()).replace(csMinLiteral, iMinBoundary.toString())
        return oReturnInfo
    }
    if (iMaxBoundary < iListSize) {
        oReturnInfo.sMessage = csShownNumbersMaximum.replace(csShownLiteral, iListSize.toString()).replace(csMaxLiteral, iMaxBoundary.toString())
        return oReturnInfo
    }
    val iaRandomSet = mutableListOf<Int>()
    for (iIndex in iMinBoundary..iMaxBoundary) { iaRandomSet.add(iIndex) }
    val iArraySize = iMaxBoundary - iMinBoundary + 1
    for (iIndex in 0 until iListSize) {
        val iTemp = iaRandomSet[iIndex]
        val iRandomIndex = Random.nextInt(iArraySize)
        iaRandomSet[iIndex] = iaRandomSet[iRandomIndex]
        iaRandomSet[iRandomIndex] = iTemp
    }
    oConfigurationsInfo.sChosenNumbers = iaRandomSet.subList(0, iListSize).joinToString(csSpaceString)
    return checkDraw()
}

fun checkDraw(): ReturnInfo {
    val oReturnInfo = permute()
    if (oReturnInfo.bSucceeded) {
        val saDrawnNumber = oConfigurationsInfo.sDrawnNumbers.split(csSpaceString)
        val iDrawnNumberCount = saDrawnNumber.size
        val iTicketCount = oConfigurationsInfo.lstTicket.size
        val iItemCount = oConfigurationsInfo.lstTicket[0].lstItem.size
        for (iIndex in 0 until iTicketCount) {
            var iMatchCount = 0
            val lstItem = oConfigurationsInfo.lstTicket[iIndex].lstItem
            for (iItemIndex in 0 until iItemCount) {
                val sItem = lstItem[iItemIndex]
                for (iDrawnNumberIndex in 0 until iDrawnNumberCount) { if (sItem == saDrawnNumber[iDrawnNumberIndex]) { iMatchCount++ } }
            }
            oConfigurationsInfo.lstTicket[iIndex].iMatchCount = iMatchCount
        }
        oReturnInfo.sMessage = headerMatch()
    }
    return oReturnInfo
}

fun randomDraw(): ReturnInfo {
    val iMinBoundary = ciMinValue
    val iMaxBoundary = oConfigurationsInfo.sMax.toInt()
    val iListSize = oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption].toInt()
    val iaRandomSet = mutableListOf<Int>()
    for (iIndex in iMinBoundary..iMaxBoundary) {
        iaRandomSet.add(iIndex)
    }
    val iArraySize = iMaxBoundary - iMinBoundary + 1
    for (iIndex in 0 until iListSize) {
        val iTemp = iaRandomSet[iIndex]
        val iRandomIndex = Random.nextInt(iArraySize)
        iaRandomSet[iIndex] = iaRandomSet[iRandomIndex]
        iaRandomSet[iRandomIndex] = iTemp
    }
    oConfigurationsInfo.sDrawnNumbers = iaRandomSet.subList(0, iListSize).joinToString(csSpaceString)
    return checkDraw()
}

fun refresh(): ReturnInfo {
    val oReturnInfo = ReturnInfo(csEmptyString, false)
    oConfigurationsInfo.sDrawnNumbers = getCleanSet(oConfigurationsInfo.sDrawnNumbers)
    val saDrawnNumbers = oConfigurationsInfo.sDrawnNumbers.split(csSpaceString)
    val iDrawnNumbersSize = saDrawnNumbers.size
    val iPickSize = oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption].toInt()
    if ((oConfigurationsInfo.sChosenNumbers + oConfigurationsInfo.sDrawnNumbers) == csEmptyString) {
        oReturnInfo.sMessage = headerMatch()
        return oReturnInfo
    }
    if (iDrawnNumbersSize != iPickSize) {
        oReturnInfo.sMessage = csShownNumbersMinimum.replace(csShownLiteral, iDrawnNumbersSize.toString())
            .replace(csMinLiteral, iPickSize.toString())
        return oReturnInfo
    }
    if (iDrawnNumbersSize < oConfigurationsInfo.iMinUniverseSize) {
        oReturnInfo.sMessage = csShownNumbersMinimum.replace(csShownLiteral, iDrawnNumbersSize.toString())
            .replace(csMinLiteral, oConfigurationsInfo.iMinUniverseSize.toString())
        return oReturnInfo
    }
    if (iDrawnNumbersSize > oConfigurationsInfo.iMaxUniverseSize) {
        oReturnInfo.sMessage = csShownNumbersMaximum.replace(csShownLiteral, iDrawnNumbersSize.toString())
            .replace(csMaxLiteral, oConfigurationsInfo.iMaxUniverseSize.toString())
        return oReturnInfo
    }
    for (iIndex in 0 until iDrawnNumbersSize) {
        val sDrawn = saDrawnNumbers[iIndex]
        if (!sDrawn.isDigitsOnly()) {
            oReturnInfo.sMessage = csItemIsNotANumber.replace(csItemLiteral, sDrawn)
            return oReturnInfo
        }
    }
    return checkDraw()
}

fun getMatchedTicketList():List<TicketInfo> {
    val lstMatchedTicket = mutableListOf<TicketInfo>()
    val iMatchThreshold = oConfigurationsInfo.lMatchOptions[oConfigurationsInfo.iMatchSelectedOption].toInt()
    for (stTicket in oConfigurationsInfo.lstTicket) { if (stTicket.iMatchCount >= iMatchThreshold) { lstMatchedTicket.add(stTicket) } }
    oConfigurationsInfo.lstMatchedTicket = lstMatchedTicket
    if (oConfigurationsInfo.bShowAllFlag) {
        return oConfigurationsInfo.lstTicket
    }
    return oConfigurationsInfo.lstMatchedTicket
}

fun getCopyString(lstTicket: List<TicketInfo>): String {
    var sMessage = csClipboardHeader.replace(csChosenLiteral, oConfigurationsInfo.sChosenNumbers).replace(csDrawnLiteral, oConfigurationsInfo.sDrawnNumbers)
    for (stTicket in lstTicket) { sMessage += csClipboardTicket.replace(csIdLiteral, stTicket.sTicketId)
        .replace(csMatchLiteral, stTicket.iMatchCount.toString()).replace(csTicketLiteral, stTicket.lstItem.joinToString(csCommaSpaceString))
    }
    return sMessage
}

fun summarizeMatch(): String {
    val iaMatchCount = mutableStateListOf(0, 0, 0, 0, 0, 0, 0)
    for (stTicket in oConfigurationsInfo.lstTicket) { iaMatchCount[stTicket.iMatchCount] += 1 }
    return (if (oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption] == "5") cs5Summary else cs6Summary)
        .replace(csTicketsLiteral, oConfigurationsInfo.lstTicket.size.toString())
        .replace(csSixLiteral, iaMatchCount[6].toString()).replace(csFiveLiteral, iaMatchCount[5].toString()).replace(csFourLiteral, iaMatchCount[4].toString())
        .replace(csThreeLiteral, iaMatchCount[3].toString()).replace(csTwoLiteral, iaMatchCount[2].toString()).replace(csOneLiteral, iaMatchCount[1].toString())
        .replace(csZeroLiteral, iaMatchCount[0].toString())
}

fun headerMatch(): String {
    val iaMatchCount = mutableStateListOf(0, 0, 0, 0, 0, 0, 0)
    for (stTicket in oConfigurationsInfo.lstTicket) { iaMatchCount[stTicket.iMatchCount] += 1 }
    return (if (oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption] == "5") cs5Header else cs6Header)
        .replace(csTicketsLiteral, oConfigurationsInfo.lstTicket.size.toString())
        .replace(csSixLiteral, iaMatchCount[6].toString()).replace(csFiveLiteral, iaMatchCount[5].toString()).replace(csFourLiteral, iaMatchCount[4].toString())
        .replace(csThreeLiteral, iaMatchCount[3].toString()).replace(csTwoLiteral, iaMatchCount[2].toString()).replace(csOneLiteral, iaMatchCount[1].toString())
        .replace(csZeroLiteral, iaMatchCount[0].toString())
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { Drop10Theme { Drop10App() } } }
}
//videos
//voice
//utube
//18 or older
//store

//var csDefaultPattern = readTextFromDisk(sFileName = csDefaultPatternFileName)
//if (csDefaultPattern == csEmptyString) {
//    csDefaultPattern = "5,2,5\n0,1,2,3,4\n\n5,2,6\n3,0,1,2,4\n3,0,1,2,5\n0,1,2,4,5\n\n5,2,7\n0,1,2,3,4\n0,1,2,5,6\n0,3,4,5,6\n\n5,2,8\n0,4,1,2,3\n0,4,5,6,7\n0,1,2,3,7\n1,2,3,5,6\n\n5,2,9\n7,0,1,2,8\n7,3,4,5,6\n0,1,2,3,4\n0,1,2,5,6\n8,3,4,5,6\n\n5,2,10\n0,5,6,1,7\n0,6,2,3,4\n0,1,7,2,8\n0,8,3,4,9\n5,6,2,8,9\n5,1,2,3,4\n1,7,3,4,9\n\n5,3,5\n0,1,2,3,4\n\n5,3,6\n2,3,0,1,4\n2,3,0,1,5\n2,0,1,4,5\n3,0,1,4,5\n\n5,3,7\n4,0,1,2,3\n4,0,1,5,6\n4,2,3,5,6\n0,1,2,3,5\n0,1,2,3,6\n\n5,3,8\n0,1,2,3,4\n0,1,5,6,7\n0,2,3,5,7\n0,2,4,5,6\n0,3,4,6,7\n1,2,3,5,6\n1,2,4,6,7\n1,3,4,5,7\n\n5,3,9\n6,0,7,1,8\n6,0,1,2,5\n6,0,2,3,4\n6,7,8,2,3\n6,7,8,4,5\n6,1,3,4,5\n0,7,1,3,4\n0,7,2,4,5\n0,1,8,2,4\n0,1,8,3,5\n7,1,2,3,5\n8,2,3,4,5\n\n5,3,10\n4,5,0,6,1\n4,5,0,9,3\n4,5,6,9,2\n4,5,7,8,9\n4,0,6,7,8\n4,0,1,9,2\n4,0,7,2,3\n4,6,1,8,3\n4,1,7,8,2\n5,0,6,7,2\n5,0,1,7,9\n5,0,1,8,3\n5,6,8,2,3\n5,1,7,2,3\n0,6,1,2,3\n0,6,8,9,3\n0,1,8,9,2\n6,1,7,9,3\n7,8,9,2,3\n\n5,4,5\n0,1,2,3,4\n\n5,4,6\n1,0,2,3,4\n1,0,2,3,5\n1,0,2,4,5\n1,0,3,4,5\n0,2,3,4,5\n\n5,4,7\n0,3,4,5,6\n0,3,4,1,2\n0,3,5,1,2\n0,3,6,1,2\n0,4,5,1,2\n0,4,6,1,2\n0,5,6,1,2\n3,4,5,6,1\n3,4,5,6,2\n\n5,4,8\n0,1,6,2,3\n0,1,6,2,5\n0,1,6,2,7\n0,1,6,4,5\n0,1,6,4,7\n0,1,2,3,4\n0,1,2,3,5\n0,1,3,4,7\n0,1,4,5,7\n0,6,2,3,4\n0,6,3,5,7\n0,2,3,5,7\n0,2,4,5,7\n0,3,4,5,7\n1,6,2,4,5\n1,6,3,4,5\n1,6,3,5,7\n1,2,3,4,7\n1,2,3,5,7\n6,2,3,4,5\n6,2,3,4,7\n6,2,4,5,7\n\n5,4,9\n7,3,0,1,4\n7,3,0,1,2\n7,3,0,5,2\n7,3,0,6,8\n7,3,1,5,8\n7,3,1,6,2\n7,3,4,5,6\n7,3,4,8,2\n7,0,1,5,6\n7,0,1,8,2\n7,0,4,5,8\n7,0,4,6,2\n7,1,4,5,2\n7,1,4,6,8\n7,4,5,6,2\n7,5,6,8,2\n3,0,1,4,5\n3,0,1,4,6\n3,0,1,4,8\n3,0,4,5,2\n3,0,5,6,2\n3,0,5,8,2\n3,1,4,6,2\n3,1,5,6,2\n3,1,6,8,2\n3,4,5,6,8\n0,1,4,5,6\n0,1,4,8,2\n0,1,5,6,8\n0,1,5,6,2\n0,4,6,8,2\n1,4,5,8,2\n\n5,4,10\n6,7,3,4,0\n6,7,3,1,2\n6,7,3,5,8\n6,7,3,5,9\n6,7,3,8,9\n6,7,4,1,5\n6,7,4,2,8\n6,7,4,2,9\n6,7,0,1,8\n6,7,0,1,9\n6,7,0,2,5\n6,7,5,8,9\n6,3,4,0,9\n6,3,4,1,8\n6,3,4,2,5\n6,3,0,1,5\n6,3,0,2,8\n6,3,1,2,9\n6,4,0,1,2\n6,4,0,5,8\n6,4,1,5,9\n6,4,2,8,9\n6,0,1,8,9\n6,0,2,5,9\n6,1,2,5,8\n7,3,4,0,8\n7,3,4,1,9\n7,3,4,2,5\n7,3,0,1,5\n7,3,0,2,9\n7,3,1,2,8\n7,4,0,1,2\n7,4,0,5,9\n7,4,0,8,9\n7,4,1,5,8\n7,0,2,5,8\n7,1,2,5,9\n7,1,2,8,9\n3,4,0,1,2\n3,4,0,1,5\n3,4,0,8,9\n3,4,2,5,8\n3,4,2,5,9\n3,0,1,2,5\n3,0,1,5,8\n3,0,1,5,9\n3,1,2,8,9\n3,2,5,8,9\n4,0,1,2,5\n4,0,1,2,8\n4,0,1,2,9\n4,1,5,8,9\n0,2,5,8,9\n\n6,2,6\n0,1,2,3,4,5\n\n6,2,7\n4,0,1,2,3,5\n4,0,1,2,3,6\n0,1,2,3,5,6\n\n6,2,8\n0,1,2,3,4,5\n0,1,2,3,6,7\n0,1,4,5,6,7\n\n6,2,9\n0,1,2,3,4,5\n0,1,2,6,7,8\n3,4,5,6,7,8\n\n6,2,10\n0,1,2,3,4,5\n0,1,2,3,7,8\n0,1,6,7,8,9\n2,3,4,5,6,9\n4,5,6,7,8,9\n\n6,3,6\n0,1,2,3,4,5\n\n6,3,7\n3,4,0,1,2,5\n3,4,0,1,2,6\n3,0,1,2,5,6\n4,0,1,2,5,6\n\n6,3,8\n0,1,2,3,4,5\n0,1,2,3,6,7\n0,1,4,5,6,7\n2,3,4,5,6,7\n\n6,3,9\n0,1,2,3,4,5\n0,1,2,3,4,6\n0,1,2,3,4,7\n0,1,2,3,4,8\n0,1,5,6,7,8\n0,4,5,6,7,8\n2,3,5,6,7,8\n\n6,3,10\n0,1,2,3,4,9\n0,1,2,4,5,7\n0,1,2,6,8,9\n0,1,5,6,7,9\n0,3,4,6,7,8\n0,3,5,7,8,9\n1,2,3,5,7,8\n1,3,4,5,6,8\n2,3,4,6,7,9\n2,4,5,6,8,9\n\n6,4,6\n0,1,2,3,4,5\n\n6,4,7\n0,1,2,3,4,5\n0,1,2,3,4,6\n0,1,2,3,5,6\n0,1,2,4,5,6\n0,1,3,4,5,6\n\n6,4,8\n0,1,2,3,4,5\n0,1,2,3,4,7\n0,1,2,4,5,6\n0,1,3,4,6,7\n0,1,4,5,6,7\n0,2,3,5,6,7\n1,2,3,5,6,7\n2,3,4,5,6,7\n\n6,4,9\n0,1,2,3,4,5\n0,1,2,6,7,8\n0,1,3,4,6,7\n0,1,3,5,7,8\n0,1,4,5,6,8\n0,2,3,4,7,8\n0,2,3,5,6,8\n0,2,4,5,6,7\n1,2,3,4,6,8\n1,2,3,5,6,7\n1,2,4,5,7,8\n3,4,5,6,7,8\n\n6,4,10\n0,1,2,3,5,7\n0,1,2,3,8,9\n0,1,2,4,5,8\n0,1,2,5,6,9\n0,1,3,4,6,8\n0,1,4,7,8,9\n0,1,5,6,7,8\n0,2,3,4,6,9\n0,2,4,5,7,9\n0,2,6,7,8,9\n0,3,4,5,6,7\n0,3,5,7,8,9\n1,2,3,4,5,6\n1,2,3,4,7,9\n1,2,3,6,7,8\n1,3,5,6,8,9\n1,4,5,6,7,9\n2,3,4,5,8,9\n2,4,5,6,7,8\n3,4,6,7,8,9"
//    saveTextToDisk(sFileName = csDefaultPatternFileName, sText = csDefaultPattern)
//}
//replacePatternCode(sFileName = "code20.txt")
//val sCode = readTextFromDisk(sFileName = oConfigurationsInfo.sCodeFileName)
//if (sCode == csEmptyString) processPatternCode()

//fun replacePatternCode(sFileName: String): String {
//    processPatternCode(readTextFromDisk(sFileName = sFileName))
//    refreshPatternFiles(
//        sCodeFileName = sFileName,
//        sConfigurationPickValue = oConfigurationsInfo.lPickOptions[oConfigurationsInfo.iPickSelectedOption],
//        sConfigurationMatchValue = oConfigurationsInfo.lMatchOptions[oConfigurationsInfo.iMatchSelectedOption],
//        sConfigurationListSize = (oConfigurationsInfo.iMaxUniverseSize + 1).toString()
//    )
//    saveTextToDisk(sFileName = oConfigurationsInfo.sStateFileName, sText = convertConfigurationToString())
//    return csPatternCodeReset
//}

//fun processPatternCode(psCode:String) {
//    var sCode = psCode
//    oConfigurationsInfo.iMaxUniverseSize = ciAdBoundary
//    for (iIndex in 0 until 10) { sCode = sCode.replace(iIndex.toString() + csCommaString, csKey1[iIndex].toString()) }
//    for (iIndex in 0 until 10) { sCode = sCode.replace(iIndex.toString() + csEOLNString, csKey2[iIndex].toString()) }
//    saveTextToDisk(sText = sCode.replace(ciEOLNCharacter, csKey3[0]).replace('1', csKey4[0]).replace('2', csKey5[0]).replace('3', csKey6[0]),
//        sFileName = oConfigurationsInfo.sPatternFileName)
//}