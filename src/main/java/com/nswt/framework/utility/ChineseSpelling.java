package com.nswt.framework.utility;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * ÖÐÎÄÆ´µ¥´¦ÀíÀà
 * 
 * @Author ÍõÓý´º
 * @Date 2007-7-2
 * @Mail nswt@nswt.com.cn
 */
public class ChineseSpelling {
	private static Mapx HZXS = new Mapx();
	static {
		HZXS.put("µÔ", "Zhai");
		HZXS.put("ÆÓ", "Piao");
		HZXS.put("çÑ", "Miao");
		HZXS.put("²·", "Bu");
		HZXS.put("î¡", "Sui");
		HZXS.put("Î¾", "Yu");
		HZXS.put("ê°", "Zang");
		HZXS.put("±¡", "Bo");
		HZXS.put("öÄ", "Qu");
		HZXS.put("Ôø", "Zeng");
		HZXS.put("ÅË", "Pan");
		HZXS.put("µ¥", "Shan");
		HZXS.put("Ýï", "Hao");
		HZXS.put("åÖ", "Lu");

		HZXS.put("½â", "Xie");
		HZXS.put("²é", "Zha");
		HZXS.put("ì¶", "Yu");
		HZXS.put("Úó", "Kui");
		HZXS.put("ÕÛ", "She");
		HZXS.put("ãÚ", "Que");
		HZXS.put("Çø", "Ou");
		HZXS.put("³ð", "Qiu");
		HZXS.put("ò¡", "Xie");

		HZXS.put("¸Ç", "Ge");
		HZXS.put("åÌ", "Pang");
		HZXS.put("ãë", "Wen");
		HZXS.put("ÀÖ", "Yue");
		HZXS.put("ãÛ", "Kan");
		HZXS.put("íõ", "Sui");
		HZXS.put("å£", "Tan");

		HZXS.put("åµ", "Mi");
		HZXS.put("Îµ", "Yu");
		HZXS.put("Û­", "Xi");
		HZXS.put("ÖÖ", "Chong");
		HZXS.put("°Ø", "Bo");
		HZXS.put("Ø¿", "Nie");
		HZXS.put("Ù¤", "Qie");
		HZXS.put("²Ø", "Zang");

		HZXS.put("³¯", "Zhao");
		HZXS.put("À‰", "Xian");
		HZXS.put("Ù¦", "Nai");
		HZXS.put("µ—", "Chu");
		HZXS.put("å£", "Tan");
	}

	private static String[][] HZALL = new String[][] {
			new String[] { "A", "°¡°¢ºÇß¹àÄëçï¹åH" },
			new String[] { "Ai",
					"°®°«°¤°¥°­°©°¬°¦°§°ª°¯°£°¨´ôàÉæÈè¨êÓÞßíÁàÈïÍö°VÄË´ƒvƒŒƒùØÜ„’…¥ßÀ…Ù†‡Bàæ‡†ˆì‰a‰¹ÆæŠÖŠâ‹ÜÌÛ‘°‘¹”±”²•l•á™üšGš±œÜœâžGŸCŸs­a°}°Š²}³v´oµK½iËBÌ@ÖL×c×rÙŒÜtá{æXèPéuºÒêiêqëBì\ìaðgñLòIöJ÷oø" },
			new String[] { "An", "°´°²°µ°¶°³°¸°°°±°·³§¹ãâÖÞîáíï§èñÚÏðÆÛû÷öóƒ‡…\…{†H†††±ˆˆ¥ˆÝ‹F‹jŒå^¸É••›¡«q¯uºÐ±Q±V´UÁOÄWÇIÈCÈsÈ€ÉŽÑsÕYÖOØtØßVãQä@åBÇ¯éœêŽê›ë@ëˆì”íí™îOñüñKõcø‘ùgù“" },
			new String[] { "Ang", "°º°¹°»Ñö…nŒì•n–‹áZálóa" },
			new String[] { "Ao", "°À°¼°Á°Â°¾°Ã°½°¿°ÄÏùÞÖæÁâÚæñà»ÛêåÛñúòüéáöË÷¡÷éá®…†õàÞ‡Æ‡Ìˆ‰¥‰§ŠSŠW‹‹‹®CåŽS‘R’U’j“³“ý–À—`¹÷›|½½E²ÁŸÑ nª‡­H±l´x´“´ÂKÂOÆbÊTÎ‚Ò\Ö’Ö“ÝEàUçGéOëJòˆö—ø^ø€úqü" },
			new String[] { "Ba", "°Ñ°Ë°É°Ö°Î°Õ°Ï°Í°Å°Ç°Ó°Ô°È°Ð°Ê°Ì°Ò°ÆôÎÜØá±öÑîÙ÷ÉÝÃå±”²®…©†\†^ˆzˆ¢‰‰ÎŠBŠ‚Qy’i’pÞã–[èË–Â™ñÅÈžß ã«X°j°q³F¸Ÿ¼“ÁTÁjÃ_ÆžÝÉÍMÒ†ÔyØ^Ú•ÝRá—ášâZïTôƒõEõN÷„÷ˆü–" },
			new String[] { "Bai", "°Ù°×°Ú°Ü°Ø°Ý°Û²®°ÞÞãßÂêþ†hŽß°Ç’…’“ÅÅ”[”¡–àÅÉªW¸q»“»Ÿ½]ÞµËbÒoÙ”ì‹÷¹ív" },
			new String[] { "Ban", "°ë°ì°à°ã°è°á°æ°ß°å°é°â°ç°ê°ä°íñ­ÛàîÓô²Úæñ£K·ÖˆmˆÐŠ”Œê±òE“„”‘”Ê•L–D–®œ°­š¶t»O»{½OÃRÎZÎ†ÎŒÑ—ÒƒáÙÛAÞkÞl±æ±çÞnÞqâkã[é›ì‡îCô‘øX" },
			new String[] { "Bang", "°ï°ô°ó°õ°÷°î°ñ°ö°ø°ð°ò°ùäºÝòK†çˆ ˆÈ‰Y‹˜LŽ°ŽÀŽÍÅíÏ’²’Ê“sÅÔ—” ¥«g³‰¶œ¼½‰¿R·ÄÅÍKÍ{ÎMó¦Örß™æ^íDòuóo" },
			new String[] { "Bao", "°ü±§±¨±¥±£±©±¡±¦±¬°þ±ªÅÙ±¢°ý±¤°ú°û±«ÅÚÆÙöµæßìÒñÙð±õÀÝáÜƒ˜„ƒÙè„ô´ô‡E‡¥ˆçˆó‹~‹›Œ‡Œ—ŒšÞA•Þ–¢«’³h·‘¸²¾¾‹Ç˜Ê}ËÌ™ÍdÐˆÅÛÙöÑfÒJÙ…ãEètè˜é–ìdìsï’ï–ñhóbóŽõUøRødý_å²" },
			new String[] { "Be", "È`" },
			new String[] { "Bei",
					"±»±±±¶±­±³±¯±¸±®±°±´±²±µ±º±·±¹±ÛñØã£ÝíðÇöÍßÂÚý÷¹ØÃÚéíÕ‚pÙÂ‚³‚Ë‚äƒF†\†h†Õˆ¢ÛýâöÊ‘v“d•K–{–È—G—f—“—”—À²¨ ´ ÍªN¬D¬i¯w° ¶F¹t¼LÆpÆ…ÆÐÝÉÈiÆÏËÍ“òãÒoÕRÕ|Ø°ÏÝKÝ…àfãmä^åCèEócùl" },
			new String[] { "Ben", "±¾±¼±½±¿º»ï¼êÚÛÎÛÐÌå‚–†Ï‰úŠM’Ù“à—L—ñ›yœ`žÇŸø ÄªŠÁÏnÙSÝ™ßGåQèM" },
			new String[] { "Beng", "±Ä±Á±Â±À±Å°ö±Ãê´àÔÈ°ø‚õßô†çˆ©ˆÈÜ¡‰lŠRÐÅê’²“s°ñmŸÔ¬a¬e¯nµp½l¾X¿‡ÈEÛMßJåAçaéGéaìž" },
			new String[] {
					"Bi",
					"±È±Ê±Õ±Ç±Ì±Ø±Ü±Æ±Ï±Û±Ë±É±Ú±Í±Ò±×±Ù±Î±Ð±Ó±Ö±Ý±Ñ±ÔÃØÃÚïõÞµÝ©ÜÅÝÉØ°ñÔî¯ÙÂæÔáùóÙóëó÷ô°ÜêôÅâØîéõÏßÁã¹êÚääå¨èµßÙ÷Âåöåþæ¾ØòÓØ·ð‚¿„ö±°…ñ†ž†ôˆfÛýˆã¸´‰ýŠ`ŠŒŠËæÇ‹ïŒÂš·ùŽÅŽÆâÏYŒ’PWÏ·÷Þã”À”è·þ–aèÁ–Š–©–Ä—a—À—ééÞéèšÈ›a²¨œ œü§Ÿ•ŸÎª‹ªŒ«®n®w®…¯H¯R¯w°n°zÆ³µ–·K¸“¹P¹t¹u¹vº`»z»¼„¼ž¾a¿oÀVç¢ÁTÁXÁ‘ÂÃYÃZÃ^Æ¢ÄMÄbÆƒÈ]ÉœÌYÂÇÍšÎ“Ð‹±»ÒKÒgÓvÔv×ØPØ„ÙCÙMÙSÚF·ÑÚPõËÛ~Û‹ÜKÜLß›àˆàŠâtãGåCæqç@èEöÍé[é\é]ésê\ÚéÚðì‹í@íSí{ïð{ñEð¥ñƒòóôxô“õIõmös÷”øpùSù›úzú‡ûGü„" },
			new String[] { "Bia", "÷Ô" },
			new String[] { "Bian", "±ß±ä±ã±é±à±ç±â±á±Þ±å±æ±èâíí¾ØÒãêíÜòùñÛöýóÖÜÐñ¹ÛÍçÂìÔ‰ä·âOÌÆ’\ÞÕ“O•c›Mž× ¤ªpª ®K¯V·Hð¡¹»e¼D¾Ž¾œÅXÅŒÈqËxÒŒÓS×ƒØPÙHÞgÞkÞlÞpÞqß„ß…ß›áŠæQérì™îYöböcøuú@÷Ô" },
			new String[] { "Biao", "±í±ê±ë±ìè¼æ»ì©ì­÷§ñ¦ì®ïðñÑæôïÚ‚lƒGƒšØâ‰wæÎŽ¼Ò“¿˜Ë™~œWœýÆ¯ždìáŸÏ gªY·…ºgÃ ÄrÅA°úÊEË‘ÒFÕ•Ö€Ù™ål÷éçSèsïRï[ïjïkïlïnòŠóQóT÷Bû÷Ô" },
			new String[] { "Bie", "±ð±ï±î±ñõ¿„e…ñ•Ö°Ç°Î°Æ“ÅÆ²–Â–Äªm°TÃØ·ÆƒÇa±ÎÌ‹ÍrÏhÒX÷Mü‚ý–" },
			new String[] { "Bin", "±ö±ô±÷±ò±ó±õáÙë÷éëçÍ÷ÆÙÏéÄ÷ÞïÙçã·Ýƒ†”P—Ãš›šàšñäºžIžMžl¬ž­p³WÀ_ÄœÌžÏ™ÓŸØhÙeÙfÙšÚSß“è\ìEîlî Æµóxó‰óôW" },
			new String[] { "Bing", "²¢²¡±ø±ù±û±ýÆÁ±ü±ú±þÞðéÄÙ÷ÚûK•ã‚v‚§‚ìÙûˆ—Œ}Æ½ŽÕŽðT’mÆ´’ò•\•mèÊ–Þ–â—€—Š™‰šê ]ìÞ¬V¯n°R°S±}·A·’¸p½l½Žç®ÆuÍsÕ@ÛMâãuä‰êvìhìí@íSïžðVõmðÚ" },
			new String[] {
					"Bo",
					"²¦²¨²¥²´²©²®²µ²£°þ±¡²ª²¤²§²«²±²¯°ã°Ø²°²³²¬²­²²ÆÇ²·íçõËéÞÙñð¾õÛà£Þ¬ô¤îàâÄë¢·ð‚Nƒ`ƒk„ƒÄ¼†\‡h‡¥‰®Š‚ØÃŒXóŽ“°ÅÂö‘ÅÅÄ°Î’©“Üß¨±©·þ–Â—K˜_™q™Øš†ÆÃ›Âœ_œ”ŠÅËÆÙŸ¹±¬ ¦ Ý é þªt­“­”·¬°h°l°×°Ù°±C³j´B´‘µR¶z·q¸Ÿ¹º~²¾¼\¼žÀÃJÃ`Å‡åõÆtÆ…ÆžÆÐÈ•ÆÑÊNÊXÞµÞÁÌYÍoÐ“ÑBÑJÒTÒUÒqÔy×LØmõÀÅÜÜ@àRâ“ãKã\äcænè}éDðGðoñAñCñFñgñ•ò’ómópõEõN÷QöÑ÷ˆùPêþ" },
			new String[] { "Bu", "²»²½²¹²¼²¿²¶²·²¾²¸±¤²º²ÀÆÒê³åÍêÎîßîÐõ³ß²ÑƒW„Ï…Ä…ùˆ¶ŠçŒ mŽïE‹’pÞÔ’Ã’Ñ“ä“ò–¿šhšiäßªŽ¶¹rº^Ç[Éž±¡ÑaÕcÛYÝ•ÞKà^âbâ˜¸½ê†÷¹ðJðXõ‹øGùLûQ" },
			new String[] { "Ca", "²Á²ðíåàê‚ð‡Í”c™U´~µg²Ìßn" },
			new String[] { "Cai", "²Å²Ë²É²Ä²Æ²Ã²Â²È²Ç²Ì²Ê‚š‚Æ†’ˆÆŠéŒu‘å’A’ñ“H—¾Z¿nÀuØ”ÛP" },
			new String[] { "Can", "²Ï²Ð²ô²Î²Ò²Ñ²Í²Óæîè²åî÷õôÓ‚ðƒ……¢…£…¤†Ð‡A‡k‡Ô‹Û‹ìß‘K‘L‘M‘”‘â“·•üšˆœ\œ’Ó N |·_ºdËLÎ]ÐQÐTÖÛŠçDï{ïŠò‰öYöŸ÷“üo" },
			new String[] { "Cang", "²Ø²Ö²×²Õ²ÔØ÷¨‚}‚áƒû…MÈ™âœæžPª¬šº[À˜ê°Å“ÉnÊiÏ@Ù‰è†úIû]" },
			new String[] { "Cao", "²Ý²Ù²Ü²Û²ÚàÐô½ó©äî‚óåøæóý‘F‘¨“Ù•ùÔèÃHÆHÜ³É˜ÒGÒ_Ôìà“ç[èAòxü" },
			new String[] { "Ce", "²á²à²ß²â²Þâü‚ÈƒÔ…‹‰x‹¨àýŽ¾ŽúÅ‘Š’‘”˜–ÅÕ¤œy®‚¸ž¹Z¹k¹‹ºu»ÇRÈYÈmÉƒÉâývØÖ" },
			new String[] { "Cen", "²Îá¯ä¹…¢…£…¤ß—q›N³•·_¸’ºdÄ~" },
			new String[] { "Ceng", "Ôø²ã²äàáÉ®ÔöŒÓò™I¸}¿•çÕòš" },
			new String[] { "Ceok", "³€³’" },
			new String[] { "Ceom", "K" },
			new String[] { "Ceon", "ªe" },
			new String[] { "Ceor", "u" },
			new String[] { "Cha", "²é²å²æ²è²î²í²ë²ì²ç²êÉ²²ïé«é¶ïïñÃãââÇéßæ±è¾ïÊàêš÷‚²„x†âÍÁˆ“ŠgŒð¿’K’Q’·’¼½Ý½Ó“c“ Ð±–Ë—^âª®›¶g¼pÃPÅaÅ‘ÆOÜÚÇNÝ±Ñ–ÓÔˆÔŒÛ‚âOã˜åšæ\èdîÎìxðlÔû" },
			new String[] { "Chai", "²ñ²ð²î²òîÎðûò²Ù­ƒŠ„Ð²æ†¶‡Ð’K´ê²é åµ}ÜëÆÊOÏŠÐƒÓâOýbö·" },
			new String[] {
					"Chan",
					"²ú²ø²ô²ó²û²ü²ù²÷²õµ¥²öêèæ¿ÝÛÚÆÙæâÜåîó¸åñïââãäýìøæöõðå¤P×ƒ]ƒdƒ{ƒ§ƒ·ÍÃ„i„}„•„­…gÀå†®†Î‡c‡Á‡ÏˆF‰‰Ê‹ÈæÓÕ¸ÝãäŽfŽÂŽÊ‘„‘Ï‘Ôµ§“˜“·“½“Û”v”â•C—{—˜^™Ùš´Õ´›º½¥œµIu¨žežž¬ŸžŸíª†®a®b³ƒ´v¶Uºo¾g¿CÀAÀWÀpÀsÕÍÃˆÆBÉ»ÊrÏMÏsÏ€ÐŸñÏÑgÒRÒbÒcÒ—ÕSÕ~Ö×€×‹×ÚßÛ…Þ{àšàžápã@äaäiçPèéKéˆêUí]îð’" },
			new String[] { "Chang", "³¤³ª³£³¡³§³¢³¦³©²ý³¨³«³¥²þÉÑöðë©ÝÅã®æÏáäÛËãÑâêØöêÆÜÉæ½Ÿ‚tÌÈƒYƒ”ƒ¯ƒ¸…”‡L‡Ÿˆö‰jŒ¬ÉÐS•˜•³—–—ÇÌÊœCŸ…«`¬d¬„¬ ®D®^®˜Ã›ÄcÄqÈOÏ^ÑmÕkää–å_çLè éLéMé‹êOíoöK÷l÷•ü" },
			new String[] { "Chao", "³¯³­³¬³³³±³²³´³°½Ë´Â³®â÷ìÌñéêËž£„¤„àßë‡ZŽlŽz€“¼˜©˜È™ùÌÎRýŸqŸ· Ÿ±|¸J»}½B¾K¾b¿U¿žç§ÉÜÁVÓeÔNÖaÖšÖßÚ}ÚˆÞCà}ânûžü{ü…" },
			new String[] { "Che", "³µ³·³¶³¸³¹³ß³ºÛåíº¼‚e‚®„ï…ã†q†Ã¶à‰ïŠbÕ¬åøØ“F“µ“Ý³â³ØŸEŸLŸc …²u³Œ³ÂsÇpÍ’ÔaÖÜ‡ÞŠîJ" },
			new String[] { "Chen",
					"³Ã³Æ³½³¼³¾³¿³Á³Â³Ä³È³À³»é´ÞÓí×Úßå·ö³àÁØ÷è¡‚E‚áƒ¡‡¸¿°‰\Ìî‰m‰}‰öÁ±×’×“Z”•æÕí—F—£—²˜¹™ÂÉòÚÈÕ¿žcŸGŸ‹¯MÕî¯„¯’íñ²_³•´~·Q¾D¿bçÇëÀëÏÆÇ_ÇkÊcËlÏIÒrÔHÕ€ÖRÖnÖ×ÙoÙ•ÚfÚ’Ú™Û{Ü•Þápâ\åŒêJ´³êëúmû‰ýYýZ³Ó" },
			new String[] {
					"Cheng",
					"³É³ËÊ¢³Å³Æ³Ç³Ì³Ê³Ï³Í³Ñ³Ò³Î³È³ÐëóèßÛôîñàáîõõ¨êÉñÎèÇòÉØ©îª\Øö‚D‚t‚ ƒ\¾»Çº†ÜˆÁˆá‰SŠ¿ŒkwáÓŽñ»Õáç‘‘r‘~‘ÍÇÀ’¬’Þ“Œ“£“¬“Î“Õ³¨–b—–—¢—¼˜ŒéÌ˜û˜ü™f™ršé›„›“ä¥›Æ›ÕœQœË¯žjžs  ª«ž¬A¬b¬š®—¶¢±²…³·Q··œ¸V¹f½†¿BÃwÃ”Ç^ÍBÏ|ÓcÕ\ÚWÚXÌËÛk±ÄàJÛ«ÐÑä…æjçdçpèKêpìlîdð‰òGòrõ“üh³Ó" },
			new String[] {
					"Chi",
					"³Ô³ß³Ù³Ø³á³Õ³à³Ý³Ü³Ö³â³Þ³Ú³Û³ã³×õØÛæÜÝÜ¯âÁæÊôùñÝë·ßêñ¡ò¿à´ð·í÷ó¤óø÷Îß³áÜó×àÍÙÑÌø‚s„„„È…h…q…µ…Õ…ä…æ…ê¶ßÐ¥Ï²†Ë‡V‡[‡i‡„ˆkˆ‰ŠLËýŠwŒÑI«¯Ópu‘J‘d‘y‘´’LÌ§’x²ð’„ÍÏ’’»ÌáÞõ“¤“¹”~–o–«èÜ˜»šIš^šlšnšýãû›nÖÎ›‚œFœ‰ÖÍœþkžÃŸUŸë ô®E¯b¯v¯€°Víô²lµoÀëÒÆ¸‡¹M¹x¹}»Œ¼Y½‚Á‹Â@ÂBÂ]ÃLëÕÃnÃqÃ’ÄSÜÎ²çÇKÀòÇ ÍNÍhÉßÎyÑDÑEÑlÔWÔ ÕBÕvÖsÖ–ÙPÚdÚmÚpÚ†Ú—ÛFÛLõ½ÛyÝBÞ‹ÞŒßWßgßoßtß†ãMãrã‰å~ëxë†ï†ï—ðSð„ÊÎñYòóPæêøTø|ùAù`ùù•ù—úuüJü[ýXýcÛ­" },
			new String[] { "Chong", "³åÖØ³æ³ä³è³çÓ¿ÖÖô¾âçô©ï¥ã¿Üû‚£‚òÙ×†Á†üˆÃŒ™ƒ×‘o“_“›˜¶›_›ÒräüÖò ‚«–¯\ÖÑµr·N¾…ÁZÁˆ¼ëÎuÏxÐnÑ~ÛŒÛ Íªã|ê™" },
			new String[] { "Chou",
					"³é³î³ô³ð³ó³í³ñ³ê³ï³ì³ë³òã°Ù±àüñ¬öÅE‚G‚¸ƒ‰…Á‡œÛÚæ¨‹B‹‹áŽÎJ‘ÀÅ¤’ôÞí“o”F–ƒ–„–ä—¹™„šŽäå b ¶ ß â®‡® °{±T±y²ƒºN»I¼—½[¾IÅWÇ“ËgÑnÔ—Öa×p×‡×‰Öß×žÛSÜPßcáOábáhá~âoÅ¥ëlô{õ\õöÖ" },
			new String[] { "Chu",
					"³ö´¦³õ³ú³ý´¥³÷³þ´¡´¢Ðó³ü´£´¤³ù³ø³ûèúèÆÛ»âðç©Ø¡ãÀõé÷íòÜéË‚m‚âƒƒ¦„IÖú‡bˆÇ‹ƒŒçŽÐN‘A‘Ã’}“ª“¹”™”ßÄû—Æ˜Z˜™[™s™Ÿ™¬™»™úšbÍ¿Êçœäéžã Ë¬G¬`­lÁòµA×£µ—¸a¸eºX½IÐõÂ^ÂaÄ•ÆcÇˆÖøÉZÉeÉÊxË ÌŽÏ{ÐEñÒÓcÓ|ÔxÕ‘ÖTÚ°ÖîØXØaØŒÚnÛHÛUÛuÜXàsãIäzézërúRúžýiýsýƒåø" },
			new String[] { "Chua", "´éšHš_" },
			new String[] { "Chuai", "´§ëúà¨àÜÞõõßšIÄDÄu" },
			new String[] { "Chuan", "´©´¬´«´®´¨´­´ªë°å×îËô­â¶çÝ‚÷ƒb„”‡ù‰@ã·Þò•ÄšNšöªk«[¬®Uº@ÄxÅxÇFÙiÛwõßÝŽâAïéúE" },
			new String[] { "Chuang", "´°´²´³´´´¯´±âë²Ö¨‚}‚ü„V„k„y„€„“‡l‡è´Ñí‘ê“œ–S™Hrw — ¡ §¯´}·™¸RÄ€ô©´ÐÊ[êJ" },
			new String[] { "Chui", "´µ´¹´¶´¸´·×µé³é¢Úï‚…‡ùˆ§·“€–û¹ŠÄDÇ”à]åNæmîqôDôsý—" },
			new String[] { "Chun", "´º´½´¿´À´¼´¾´»òíÝ»ðÈ‚¤ƒb‰@‹aÃ•I•«ëÔ–~˜J˜‡˜ê™šãç›Ìœ·œ÷_ Æ¬t²Q¹—¼ƒëÆÃaÃ‹ÄxÆXÈNÈoÉOÉ”ÙƒÛwÝbÝéúácåTêõžöjù‡ùœ" },
			new String[] { "Chuo", "´Á´ÂõÖà¨öºê¡´Ù…É‡ÇŠÅŠÆ‹C‹SŒF·’‘“óí½šf›íÄ×ìÌ´‡¹–´Ø¾Y¾b¿ž×ºÄJÜõÝýÚ}õÀÛTõâ³ùÝzÞuåÁßOáQÈ©äråYæ—èqïßýpýw" },
			new String[] { "Ci",
					"´Î´Ë´Ê´É´È´Æ´Å´Ç´Ì´ÄËÅ´Ã´Í²î×ÈßÚðËìôôÙÕè‚½„p²Þ…‹Ë¾…è†ˆˆˆôŠœ‹ãáÏÕŽãŽú´ë–c–Ÿ–²²ñ–Ü–æ›×ÌÐžB«u«y®N°rµQôÒ½a¿WÃhÜëÆ˜ÆÜùÇ„ÈWËFËjòºÍyó£ÎˆÏ…Ô~ÙnÚaÚeôôÚÞeÞiÞoâ‘ï“ð@ódóqõJøyú\ú]ý€" },
			new String[] { "Cis", "†ï" },
			new String[] { "Cong", "´Ó´Ô´Ð´Ò´Ï´ÑçýèÈäÈè®æõÜÊÙÌ…²‡èŠæŒQ¾ÀÄòS•›‘F‘m•¾—Œ˜B˜º˜Ú˜â™ßY^|ƒœžšŸtŸÐ Q ­B²j³Ÿ´°ºb¾t¾‘¿k¿v¿‚ÀS×ÝÂ‡ÂŒÂ”Æ‰ÇˆÉÊ[ËqÏZÕpÖÙzÙ{çWò^ò‹" },
			new String[] { "Cou", "´Õé¨ê£ëí×à×á×åœ«u´ØÝýÞ´Ë’Ç÷È¤Ú…Ý" },
			new String[] { "Cu", "´Ö´×´Ø´Ù×äáÞâ§Ýýõ¾õ¡éãõíÇÒ…a‡mŠÅŠÆ‹{I‘–ÆÝ’Û¯|¯•°š¿U¿qç§ÃÊIÊPÓcÕKÇ÷ÚuÈ¤Ú‚Ú…ÛUÛcÛnÛqÜAåe´íî•û€û‚û„û›üy" },
			new String[] { "Cuan", "´Ü´Ú´ÛÔÜÙàìàïéß¥ƒVŽm”e”x”€ê¿™«™çš–žUž£Ÿä·‰¸U¸ZºeºxÇˆÒ{Üfägè‰" },
			new String[] { "Cui", "´ß´à´Ý´ä´Þ´ãË¥´á´âè­ßýã²ÝÍë¥éÁtºÌå‚yƒþ†Ÿ‰…²ìŒœéõ‘N—½˜§yûŸnŸÕª‰¬X¯Q°„´…¸W»‚¼¾\¿\¿…ÀŠÁŒÃyÃœÄƒÄ‹Ä›ÒPÚ~ÛnçJö¿îx" },
			new String[] { "Cun", "´å´ç´æ¶×ââñåü„Y…¼‰–’Ž›–¿£´¸€»vÛZß—" },
			new String[] { "Cuo", "´í´é´ê´ì´ë´èáÏØÈõºëâðîõãðûï±ïó„v„z‰èÕŽó´ÝÎô×î— Ì I¬›±‘¿WÇsÇuÉcÉxÊPÌ‘ÒPÕ‹õòÜgßHßuàŸáAáiäSåeóqûzý€" },
			new String[] { "Da", "´ó´ð´ï´ò´î´ñËþóÎÞÇßÕñ×ðãâò÷°æ§í³àª÷²‡„‘…A…ì…ö‡}ˆ™Ëú‰¡‘„“‚“Ò™\šÎšùœÍžØ [®}®†±o³K¸—ÀJÁeµ¨ÇEÇQËRÏƒÓuÔzÛQÜJÞ…Þ‡µüßQß_æ]æpèNí^ý‘ý“" },
			new String[] { "Dai", "´ø´ú´ô´÷´ý´ü´þ´õ´ûµ¡´ö´ó´ùß¾çéåÊá·ß°÷ìææçªÜ¤þ…¦‡Nˆ‚Ž‘Ž¡Ž§K‘·•Î–±é¦¶¾šùžŽªy¬x¹yº‰½H¿DÅ•ÊOÍfÎ}ÏEÒyÔrÚ±ÙJÛFÛÜÜ–ÝDÞaåÖßfßrÁ¥ìOì^ñWñjñ~ÍÔõ\ølün" },
			new String[] {
					"Dan",
					"µ«µ¥µ°µ£µ¯µ§µ¨µ­µ¤µ¢µ©µªµ®µ¦µ¬Ê¯ðãå£ð÷ÝÌééíññõóìêæÙÙà¢S·‚„ƒdƒ{ƒÑÈ½„[„é…S…g…ì†m†›†²†Î‡d‡n‡~‡·Ì³‰¯ŠlŠ½‹[ŽŽ—³Àâò´×‘„‘žº¶’b“Ú“Û“ú–½éÜšKš—›X›Õ¿Ì¶å¤ÚŸí ý«m­®X¯D°D°Q°œ³N¶V·žº„¼ÀWÂnÂ›ÄEëþÄ‘ÍžÑÑÏ€ÐyÑÒRÒbÒ—êèÓgÓ”Õ²ÕQÖÙœÙ ÉÄÛÜlàáGá]ìKîFðZð…ñdñšó‡ø}ü^ülürÚàØé" },
			new String[] { "Dang", "µ±µ³µ²µµµ´ÚÔîõå´ÝÐÛÊñÉí¸‚«ƒ}‡ŽˆW³¡ˆ›ˆö‰³‹P¤ÉÕ“õ”†™n™éšë‹ÇžªÌÌ C«š¬„­T­c®G®”¯ƒ±U²^´XµD¹Yº‚ºšÅ™ÊŽÌoÏ}Òd×[×•ÚßTèKêWë‹üh" },
			new String[] { "Dao", "µ½µÀµ¹µ¶µºµÁµ¾µ·µ¿µ¼µ¸µ»àüôîâáìâë®ß¶Ù±ƒ‰ÊÜßú†ý‡‹‰»ŒpŒ§ŒàuëìŽWŽÎìýã°’Ò“v”F–]—Í˜˜™|™„ä¬ÌÎý c­±Iµ”¶\·R¹|½rÁŸÂRÅsÈKËgÍ@ÐmÐpÑnÜ„á~á’ê‰ÌÕëIëZô€÷øBÄñØÖ" },
			new String[] { "De", "µÄµØµÃµÂµ×ï½‡NÔzœ¿—‘›úµÇåuÚì" },
			new String[] { "Dei", "µÃ†O" },
			new String[] { "Dem", "“g" },
			new String[] { "Den", "’O’Y" },
			new String[] { "Deng", "µÈµÆµËµÇ³ÎµÉµÊµÅíãïëàâáØê­ô£ƒ\‰œ‹¿‘~³È™žŸô­O¸~Å˜ÓRØOà‡ç‹ëQ" },
			new String[] {
					"Di",
					"µØµÚµ×µÍµÐµÖµÎµÛµÝµÕµÜµÞµÌµÄµÓÌáµÑµÏµÒµÔµÙêëÛ¡ÚÐÚ®àÖèÜ÷¾ôÆØµé¦íûæ·Ý¶íÚïáÛæÙáíÆª‚d‚±ƒCƒ™É×…}…à†v††¬à´‡”ˆhˆkˆªˆ¯ˆ¹‰y‰„‰—ŠD‹XÞŽRftw~µK‘d’F’†’ã“W“Ÿ”³•Aè¼–m–š—\—b˜N˜µ›ÁœvœìŸb ¹«Z«Ÿ®S¯F±ƒ´Y´”µ¶Eºa¼e¼s¾†Ô¼Â‚ÃJëÕÄVÉÖÆlÆmÝ¯Ç…ÇœÉ‰ÊHÊLÊOËyË‹ÍhÎ[ÏEÐ”Ó]ÓhÔgÕœÖBØpÚdÚhÛqÛyÌãÛ‡Û—ÝBÖðÞž´þßfßmßrâKãdå~çCêsêÁ¥ì{íLîEî}ÌâñVóƒóžô†öWûM" },
			new String[] { "Dia", "àÇ" },
			new String[] { "Dian", "µãµçµêµîµíµàµßµæµâµëµìµäµèµåµáµéõÚîäÛãÚçñ²ô¡çèáÛñ°×‚Ù…Ž†ˆÛþ‰|‰«ŠHŠû‹LÑŽoŽp‘úÄé”„”“”¥—Ï˜ˆ˜•˜ë™AÕ´ÏÑœ¶Õ¬U¯t¯’°d´ÄHÉ_ÊsòÑÍŸÔaÛ†âšëŠîFîŒîò›ücý‚Ø¼" },
			new String[] { "Diao", "µôµöµðµõµñµ÷µóµïµòÄñîöï¢öôõõ®Ù¬ÙÃµ¶„aŠP‹àŒÅt‡¬’FÌô—¹š“šôœ@¬h¯š²f³H³í·–¸L¸uºyôÐ¼g½r¾I³ñÝ¯É‰ËyÍ@ÍqòèÓŽÕAÕ{ÕÔÚwÌøõÖÝUé÷ážâyäHä”åcèSëïMæôô†õMõ øBøJù@ùmûbü—" },
			new String[] { "Die", "µùµøµþµúµûµüµýëºÜ¦ð¬ÞéõÞñóöøÛìà©Øý†A†O†—U«ÞŽ²LgÂ‘ä’”’¡•i•è–»˜G˜›šŠšÛ›uÉæœhäÍ š®’®¯A¯B±y±‚ÖÏ½xÀ„ÂWÃ]ÖÁÅ\ÅŽÆ|ÎHÏHÑñÞÒBÔeÕ™ÚgÛ@ÛLÌßÛÝWéóç“èFéPíCõ]ölö÷£õÚ" },
			new String[] { "Dim", "‡Ã" },
			new String[] { "Ding", "¶¥¶¨¶¢¶©¶£¶¡¶¤¶¦¶§î®çàîúëëíÖðÛØêñôôúà¤µìŠcàŽŠâ’ð—ÅÍ¡ìµÆ®k³G´O´ÂˆÆJÈbÝãËYÍBÓ†á”äbåVç–ìwí”îrï}ð—" },
			new String[] { "Diu", "¶ªîûGäAïM" },
			new String[] { "Dong", "¶¯¶«¶®¶´¶³¶¬¶­¶°¶±¶²á¼ð´ÛíëËëØíÏë±á´ßË‚”ƒPƒö„Ó„çˆÄ‰’ŠŸŠà‹Ùd–ž‘ã’œ“_•k–|Í©—šæ›òœ§žúŸüªJð®¸•Í²¹c¹š½pÄLÆ{Ç‡ÊÎXÐhÔ˜Õ‰Þ“ÍªëšñŽòLõ[öCù…úHüŠâº" },
			new String[] { "Dou", "¶¼¶·¶¹¶º¶¸¶¶¶»¶µ¶Áò½ñ¼óûÝúc‚JƒÃ„E„r…Ê†tÍ¶”Ô–’—u™XšÃšÑ›ÃäÂž^²fñ¾¸]Ã–ÅÇW×xÓâàKáHõ¢â^äWî×ékêLêhðLðôYôZô^ô`ôa" },
			new String[] { "Du",
					"¶Á¶È¶¾¶É¶Â¶À¶Ç¶Æ¶Ä¶Ã¶Å¶½¶¼¶¿¶Ê¶Ùó¼óÆà½äÂèüë¹÷ò÷ÇÜ¶ƒ™„E„†„‹…X… ‡€ÍÁ‰TŠ‹óÕ¬Žª”¾•’•¤˜Ì˜ÐéÒ™³š˜šœ›èž^ © Ùªš¬o­{°²G¶Š¸]¸‰ºVôîÇTÎ}Î–ÐCÑtÒeÒlÓGÔŒÕi×x×˜²ïØKÙ€ÚGá`åLåƒæNèoÕàêAê^ê•ì|íbí~íîDòyüt" },
			new String[] { "Duan", "¶Î¶Ì¶Ï¶Ë¶Í¶Ðé²ìÑóý‚Ç„Œ‰F‹eåè”àš¬¬‡´Vº@»f¾„ÂZÄaÈ˜ÑƒõßÜYå‘æH" },
			new String[] { "Dui", "¶Ô¶Ó¶Ñ¶Ò¶ØïæíÔí¡í­ƒµƒ¶ˆŒˆÍ‰[¶áŠZŒŒ¦Å‘‡‘»“€–€žAžSžwž}¯y´qµq½˜Ä„ËcÖd×B×m×·âqäJä„åTæmç…çŽÈñêŒê îXø‹" },
			new String[] { "Dul", "c" },
			new String[] { "Dun", "¶Ö¶Ù¶×¶Õ¶Ø¶Û¶Ü¶Ú¶Ý²»õ»ãçíïïæíâìÀí»¯¿¡‡‰•‰ÝŽÝ÷ª‘‡“Ç“æ˜J˜ú—Ÿõ Ôª–´]ÄRÄ]ÎPëàÛvÜHÜOÞšßqâgç…çŽîDò—" },
			new String[] { "Duo",
					"¶à¶ä¶á¶æ¶ç¶â¶å¶è¶é¶Þ¶ßÍÔ¶È¶ãõâãõßÍîìñÖßáç¶šƒµƒ¶¶Ò„A„m„„„‹…¼†Æ‡š‡¾ˆ‘ˆÊ‰™‰š‰ïŠZŠb‹s‹µŒ¹“ü‘†’–’—´·´§”Ÿ”£”¦”­–\ÔÓ–m–šèÞ–ª–Ã–úé¢—Ù™EšÇ›kãûÉ¯k³›¾EÆ–ÑEÔqÕBØyÚrÛFÛGÛTÜoÜ€µ¦àâ‡åTæNèIÕàÍÓêwêyËåëDï˜ð™ñWñjôDõyùzüc" },
			new String[] {
					"E",
					"¶öÅ¶¶î¶ì¶ê¶ó¶í¶ï°¢¶ô¶ë¶ð¶ñ¶ò¶õï°ÚÌÛÑïÉãÕÝàÜÃéîæ¹Ý­öùò¦ëñãµßÀØ¬ðÊåíÑÇ„†Î±àÙ¨‚­‚Îƒ^ƒi„þ…\…v…Å…Ù†@†HÑÆ†s†‘°¡ßý†¡‡f‡Ù‡êÛëˆºˆ×ˆìˆñŠŠŠŽŠ´ŠâŠã‹jŒßŒïSk¬âÖŽþ™º‘ö“t“~“”AêÂ–•—¿™ÄšGšd´õšx›¡›áœŠ«M«¬c¯u°x±“³S³X³b³j³ríÒ´dµJ°·ÉJÊ‚ÌFÍLÎYÓFÓžÔ›ÕMÖ@×F×†Ø`Ü—ÝQÝ‘Þˆß]ß{ÒØâeä~åŠèyépé‘êiêq°¯îOîPî~î€ðIð_òFØªô‰ôŠötö÷{ø‘ùEùZù[ù˜ýLý|ý…" },
			new String[] { "En", "¶÷ÞôÝìàÅŠCWŸ¸ð†ßí" },
			new String[] { "Eng", "íE" },
			new String[] { "Eo", "˜" },
			new String[] { "Eol", "s" },
			new String[] { "Eom", "™ë" },
			new String[] { "Eos", "”ñ" },
			new String[] { "Er", "¶ø¶þ¶ú¶ù¶ü¶û·¡¶ýçíöÜð¹Ù¦åÇîïõƒ¹ƒº„n…þ†„‹èŒ©ŒªXpr–k–é–ê˜Þš¾›˜œxå¦ –»•ÂYÂxÃsÄžÇHËnÐ^ÑLÔ Ù@ÙEÚÝ[Ý‰ÞWßƒãsêzê—ëXðDñ“ó’ó“õbø" },
			new String[] { "Fa", "·¢·¨·£·¥·¦·¤·§·©ÛÒíÀá‚ëŠ‘U‘°Î²¦“Ü–ì˜ìšø›o·ºžž¬m¯V°k°l²X¸ŸÁPÁUÆžÊ†ËtÙH±ááeáwåzéyóŠóŒ" },
			new String[] { "Fan",
					"·´·¹·­·¬·¸·²·«·µ·º·±·³···¶·®·ª·¯·°ìÜÞÀî²Þ¬õìèóá¦¢³„F„G„å…K‡h‰“ŠiŠï‹Ë‹Ì‹Ñé‘Œ’BÞÕ”ó”õ–i–¯—¡—÷˜õšïšøœtJž~ž’Ÿ© í­[®‰±Fµ\¹B¹D¹ »O»o¾u¿œÁ€Ä‡ÅtÅwÅxËXó´Ï›ñÈÒTÓŒØœÜÝGÞNÞxâCçxïcïxïˆï‰÷Yú‹ë¶áë" },
			new String[] { "Fang", "·Å·¿·À·Ä·¼·½·Ã·Â·»·Á·¾îÕáÝÚúèÊô³öÐ‚ØÎˆªˆÚ”ë•P•X•\›PœE °­œ±f±}µp¼ÍKÔLÚ“â[åpë„ó„ô™ö„÷›øhúJ" },
			new String[] { "Fei",
					"·Ç·É·Ê·Ñ·Î·Ï·Ë·Í·Ð·Æ·Ì·Èóõòãëèìéåúì³áôÜÀã­ïÐö­ôäé¼äÇöîç³ðò‚n„|…Š‰ŠOŠóŠôŒÐŽüUâö·÷•h•›•Õ–F–{–É—’˜ì™J™¶œdžO éªU¬i¯X°CíÉìð¹A½E¾pç¨Ã^ÃcÃdÆ…ÜØÈQÊ„Ê†ÊˆÎNÏnÅáÑpÑqÒUÕuÙMçšêŠì]ìqïwïyð[ñIòWòaó‘öEü”ü–" },
			new String[] { "Fen",
					"·Ö·Ý·Ò·Û·Ø·Ü·ß·×·Þ·à·Ó·Ù·Ô·Õ·Úèûå¯ö÷çãÙÇ÷÷ƒf·ËÅç‡Šˆbˆe‰ž±¼Š^Š}ŒðŽŒŽËkíª‘°çÞÕ”••S–B–D–Œ—r—±™Jš\åžÇŸøŸþìÜªŠÅÎ²b³W¶l¸j¼S¼ŠÁiÁ‚Á‰ÃRÄÈ†ÉkÊˆÍ_Í`ÐvÓŸØkØrÙSêÚÜmÞMâpä—èMëVëƒîC°äðiðñBñOôš÷aøXüRüvü‹" },
			new String[] { "Feng",
					"·ç·â·ê·ì·ä·á·ã·è·ë·î·í·ï·å·æ·éí¿ÙºÛºÝ×ããßô§‚ªƒt„K„N„Oˆ©ˆù‰âŠ~Œ›o¥’¸Åõ“ž—Q—÷™l›h·º›Íœtœ˜œ½mž–žÐŸuŸ‘ŸÔ Èªh¬S®g¯‚±`´^ºA½ ¿pÃTÅ}Å‚ÇlÌXÌt°öÒƒÖSØNØSÙˆÚRåÌà•ähæ‘çQìbïLïpñTøLøPøiùiÅôüKÒ…" },
			new String[] { "Fo", "·ð–¦ˆu—‚" },
			new String[] { "Fou", "·ñó¾²»ˆ¡Švžä¼€ÀŒÀÆ]Ð[ë€ø]" },
			new String[] {
					"Fu",
					"¸±·ù·ö¸¡¸»¸£¸º·ü¸¶¸´·þ¸½¸©¸«¸°¸¿·÷·ò¸¸·û·õ·ó¸³¸¨¸®¸¯¸¹¸¾¸§¸²·ø·ô·ú·ð·ý¸µ¸¼¸¥¸¢¸¤ÊÐ¸¦¸ª¸¬¸­¸·¸ÀíëíÉÜÞõÃõÆò¶ÜÀöÖá¥ÜòäæòÝÞÔÝÊòðöûòóç¦ç¨êçî·ïûÙëôïÙìèõÝ³æÚð¥æââöìðß»Û®Ü½åõíê²»T½ö¸‚Y‚a‚¾‚¿ƒåƒì„_°üß¼…ò†b²¸‡`ˆ}ˆŽˆ¡ˆóŠmŠ•ŠÂŠï‹D‹c‹Ë‹ÑŒ @TŽˆŽ“}áÜ·Í»³N‘Ê’h’½’ÑÞå“á”ê–Ž–¢–´–Á–Â–ó—­—Ó—Ú˜_›L›^·Ð›Š›šäßºžÞŸJŸr«c«s¬M­o®i®t®w®}¯ž±G³Qµy¶O¶·J¸c¹A¹[¹r¹…º…»™¼J¼”¼›½E½n½•½š¾”¿`ÀbÁJÁÃiÄwÅ€Æ]Æ…ÇCÇXÆÎÈQÈiÈƒÉ’ÊÌ’ÍbÍkÍ|Í—ÎlÐuÐ“Ð•Ñ}Ñ‡ÒLÒiÒ„Ó‡ÔcÖDØfØ“ÙMÙxÙŽ·ÑÛ~ÝPÝoÝ•Ý—»¹ßß‘àGàMà~áKáUáœâaãRãVäžå‡å˜ïÂÚâê‚÷¹íhíví‚î\ïOïTñ€ó‘ôfõHõVõvövøDøIøWøqùfù›ûŸüAüF" },
			new String[] { "Ga", "¸Á¸ì¼Ð¸Â¿§ÔþîÅÙ¤ê¸ÞÎæØæÙßÈ‡Q«VÜˆáåmôp" },
			new String[] { "Gad", "®h" },
			new String[] { "Gai", "¸Ã¸Ä¸Ç¸Å¸Æ½æ¸Èê®ÛòØ¤Úëêà_ì„÷„ø¿ÈŠ¡Yã“©•|–qºË˜¢˜£Æû[­y®„´oµ‹½i½wëÜÇDÈ‘ÉwÔ“ØdÙWÙ^à@â}æYéuºÒêdëBº¡" },
			new String[] { "Gan", "¸Ï¸É¸Ð¸Ò¸Í¸Ê¸Î¸Ì¸Ë¸Ó¸Ñêºôûí·ðáãïÜÕß¦ç¤éÏä÷äÆÞÏÛá¸öqÇ¬xœÎ‚‰ƒ÷„Q…î¼éŒ¼Œ¿ŒÀŽÖå’Iº´”—U˜o™gº¹›N›¿lž¸«\«q°‘±Y¶’¹C¹mºTº•»ˆ½CÆQÍHÐrÔlÖPØJÚCÚMÚsÞ|âFåDïó_ôvöx÷h÷ øN" },
			new String[] { "Gang", "¸Õ¸Ö¸Ù¸Û¸×¸Ú¸Ü¸Ô¸Ø¿¸óàî¸í°„‚ˆÕˆþŒù‘Þ‘ß¿¹’â—ž˜œÏŸ€ ± Â è¯I³M´L¾VÀ“À °¹âGä“æsêlî@ñþ" },
			new String[] { "Gao", "¸ß¸ã¸æ¸å¸à¸Ý¸á¸â¸ä¸ÞÛ¬Ú¾ê½çÉØºéÀï¯éÂÞ»„Æ…Ì¾Ì‰ùz•±˜‚˜°™R™™²ºÆœõ»ª‚ªˆ°w²Gµ†µ‡¶J¶Ž·X¹l¿cÁoÅVÇÝïË›Õaä†æ€ízðpó{úkúüŽ" },
			new String[] { "Ge",
					"¸ö¸÷¸è¸î¸ç¸é¸ñ¸ó¸ô¸ï¿©¸ì¸ð¸ò¸ê¸ë¸í¸ÇÒÙºÏ¸õíÑ÷Àò¢ñËÜªò´ÛÙïÓØîô´ØªàÃëõë¡æüÛÁ½éÞà‚€„ý¿É…Ã…Ï†þ‡S¸ÁÍ‘á‘ë’M’š”R”š–q˜†™ ºÆœèœð» ³ · çªnª˜íÀ¶…¹w¼vÃIÄ—ÅZÆŒºÊÉwÍxÑ\ÓkÔ†ÖYÖgÝ‘ÞPâ›ãtãxædækæŠ¼ØîþéléwéxÕ¢ì‘íRíkíuîMòZ÷ÄôŸõiõsö÷…ømøwøùB" },
			new String[] { "Gei", "¸ø" },
			new String[] { "Gen", "¸ú¸ùßçÝ¢Ø¨ôÞ“^“j" },
			new String[] { "Geng", "¸ü¸û¾±¹£¹¢¸ý¸þ¹¡âÙöáßìç®ƒ¿º„jˆíya’ª’ù•œ—Ô›ÊŸ‰®uÓ²½b½c½Ž¾¿KÁ}ÇcÈ@ÙsÐÏàDàQîióiõ†ùˆûf" },
			new String[] { "Gib", "†Ö" },
			new String[] { "Go", "†ñ" },
			new String[] { "Gong", "¹¤¹«¹¦¹²¹­¹¥¹¬¹©¹§¹°¹±¹ª¹®¹¯¹¨ºìëÅö¡çîò¼…@…C…šßÛ†y†ß‰bŒmŽ³ÞÃã‘E’–r¸Ü–íœ|ŸË´bºT¼k¼tÁ‡ºçòËÓyØ•ÚCÚM¸ÓÜpÝ\äUì–ó•ô„ýŠý" },
			new String[] { "Gou", "¹»¹µ¹·¹³¹´¹º¹¹¹¶¹¸¾äá¸ì°èÛ÷¸êíçÃóÑÚ¸åÜæÅóôØþ‚×ƒÚÇø…^…éˆx‰òŠ¥’]¾Ð“k“Â˜‹›tœÏŸµ«vº¾—ÂTÂUÂVÆ™ÍmÐÑÓMÔ_ÔØmØxÙÝ@âhã^ëgíxõLøzûYð¶" },
			new String[] {
					"Gu",
					"¹Å¹É¹Ä¹È¹Ê¹Â¹¿¹Ã¹Ë¹Ì¹Í¹À¹¾¹Ç¹¼¹Á¹Æ¼Ö¹½èôð³ãééïáÄÝÔðÀ÷½îÜëûôþßÉöñÚ¬êôî­ì±ïÀêöðóõýòÁî¹ØÅ‚ïƒlƒó„½¸æßß†f†g†˜†Ø†åˆØ‰à‹²Œ½gHë’M’_¿Ý–¾—›˜b˜€™O™¤›}›ü»¬žJžkŸ‚ð­¸Þ°–±W³‘´hµ¶™·Y¸š¹‡ºH¼M¿SÁBÁlëÒÃ™ÅV¿àÆ‚ÉuË[ÍvÐM½ÇÔbÙZÝLÝMÝžßEââ’ådíî™ðkð ÷»õYöAøù]úXü‰" },
			new String[] { "Gua", "¹Ò¹Î¹Ï¹Ñ¹Ð¹ÓØÔßÉëÒð»èéÚ´ƒÖ„Ž„œ…³†F†J†§ˆqã·’ìšOŸ…Ÿ°½\¾ ÁGÁLÉàÆ‚ÔŸÕ ÚoÛ|ã”äTèïNïWòmøŽÀ¨" },
			new String[] { "Guai", "¹Ö¹Õ¹ÔÞâ…¨ßà‡ˆ‰øs–¡–Ê¹yÁL" },
			new String[] { "Guan", "¹Ø¹Ü¹Ù¹Û¹Ý¹ß¹Þ¹à¹Ú¹á¹×ÂÚîÂñæÝ¸ÞèäÊ÷¤ðÙÙÄO´®…jŠþ ¡‘T‘×“¥ÎÓ¹ûèæ˜À™Â™àš¯ÂÙ›Œ›ýœS‚ ƒ¬g­¯p¯°H²•µeµ¸A¹`¾]À•ÅoÈXÉFÒ‹ÓQÓ^ØžÜIÝ„ßkå]æšè…érévêKêPëqð^öŠ÷b÷}øAùJûX" },
			new String[] { "Guang", "¹â¹ã¹äèæáîßÛë×ï‚UƒZˆŠ­ŽÚV»ÐÀ©’•“Ñ”Uºá™¤™õ›²äêž»žÓžÕžÖŸD«E«‡³qÅQÅSÆšÚ‡Ý_Þ‚ã üU" },
			new String[] {
					"Gui",
					"¹é¹ó¹í¹ò¹ì¹æ¹è¹ð¹ñ¹ê¹î¹ë¹å¹ç¹ô¿þ¹ïÈ²âÑå³èíØÛ÷¬öÙð§ØÐæ£êÐóþêÁwÎ±æ‚Îƒ^„£„¥…QØÑ…T…‘ÍÛˆ’Š¹ÍÞ‹‚‹¥‹¾Ž@ŽQŽ`Ž¢Žë@i“±“Ê”Š”‹•Q–_Î¦—Ë—Î¸Å˜œ˜­˜²˜³™u™™™Æ™ÍšwšðãíÍÝœˆœÄ‘«•­Y­„°I²Z²n²z³uÆíµƒ¶W·˜¹Kºl½}ÀL»æÃvÄ„Æ—É}ÌlÍŠÎšÏjÑOÒ^ÒŽõûÓmÔŽÖdÙFÚbÚ‘õêÜ‰ßžàFé|ê{ÚóëvíWòoôhôkõq÷Z÷iøWø_ø`ý”" },
			new String[] { "Gun", "¹ö¹÷¹õöçÙòíÞçµØ­¨—œ»ë»ìœ†L¬g­e±š²O¾i¾É€ÊFÐ–ÑrÖÝåKï¿õPõ…öŠ÷¤" },
			new String[] { "Guo", "¹ý¹ú¹û¹ü¹ø¹ùÎÐÛöé¤ñøÙåâ£áÆÞâàþßÃë½òäòå»®†F†J»£†©‡Hàí‡ë‡î‡ñ‡ó‡øˆÍˆå‰Ž½˜œ«‘I´ê“”šèÛ—ë˜¡™¤»î›ýœuXã¯†²Žºl¼@¾[ÂƒÄBÄNÄsÇ‘ÊbòâÎÏXÙùÑxÝ{ß^âuä˜åèJï¾ðRðŸ" },
			new String[] { "Ha", "¹þ¸òÏºîþRÏÅºÇŠUŠožéâ³Îrãx" },
			new String[] { "Hai", "»¹º£º¦¿Èº¤º¢º§º¡º¥àËõ°ëÜì…õßÔ†ã‡¯‰h’™üŸQªn½wß€à@áVéuºÒîWò¤ï™ðŽñ”ñ›ºÙ" },
			new String[] { "Hal", "a" },
			new String[] {
					"Han",
					"º°º¬º¹º®ºººµº¨º«º¸º­º¯º©º²º±º³º´º¶º·ºªÚõÝÕÞþå«ãÛñüòÀìÊò¥êÏ÷ýœÎ‚þƒË„T³§…{…î†c†i‡•‡öˆ¥ŠÎ‹©Œå—²Ç¶å¸Ð’I”êº•~•ˆ•Â—U—c—ß˜o™÷ša›Nãï›¿›È›Û›þäÆÌ²hä÷¶Èž©Ÿß ’ªR¬H¸Ê®]°y±Ží·¸’¹b¼`ÃQÇtÊGÌkÍHÍ”ÎKÎLÎ‘Ö›ØEØJÜŽÐùâFâjäIädäwé\êRê\ënìyíní™îMîhîuî›ñHñUòAô_õAøNú[" },
			new String[] { "Hang", "ÐÐÏïº½º»º¼¿Ôñþãìç¬çñˆœŠsý”ãèì¿»ÀÇ¸‘¹V½W°¹ÆfÍaØ˜Þ†ß’ôûî@ôŒ" },
			new String[] { "Hao", "ºÃºÅºÆº¿º¾ºÂºÁºÀºÄºÑ¸äê»ò«å°àãòºàÆð©Ýïå©Þ¶‚ÛƒŸ…ë†S»£‡_‡sæ¤hˆ•a•‰•±•µ•¼•Ø—·œBœéœõ»ž®ª|ª‚¸Þ°€°‚°…°ˆØº¶m¸h»DÂGÂ|ÄzÅVÆ’ËAË^ËrÌ–Ì—Ï–Õ’×qàzæeæ€çî—ö‚" },
			new String[] {
					"He",
					"ºÍºÈºÏºÓºÌºËºÎºÇºÉºØºÕºÖºÐº×ºÊºÑºÒºÔÏÅàÀÛÀîÁôçãØò¢ÛÖÚ­æüêÂÞßÃºô…ô†J¹þ†Y† †¿†Ûà¾‡m‡˜ˆ†Šº¦ŒyP²Ô’u’š½Ò”—–­¸ñ—æšBšÎÇ¢œfœz¿Êœ¸¼ŸZŸŒŸ¿ŸÀ _ e çªC°F°±A±B¹è´E´¶…ºK»t»—¼vÀU½ÉÂG¿ÁÈMËrÞ½òÂÐ«Î˜ÏšÐŽÒ‡ÔXÔZÔ†ÖyØ€ÙRÝ`Ý éûÏ½àAãFèYéuêHãÒëa»ôìeìfìgíHîMïðgðšô]ôŸ÷…ù]ùŸúKúQûSðÀûiûýLý[ý†ý˜" },
			new String[] { "Hei", "ºÚàË‹Ï¦ü\ºÙ" },
			new String[] { "Hen", "ºÜºÝºÞºÛäßç‡Œ’‹ÏÆôÞÔ‹ì•" },
			new String[] { "Heng", "ºáºãºßºâºàÐÐèìçñÞ¿ä†‘ˆýŠ¬a™M›êžîªBÃtÃ†ÙêèUø’ùCûa" },
			new String[] { "Ho", "Y" },
			new String[] { "Hol", "b" },
			new String[] { "Hong",
					"ºìºäºåºçºéºêºæºèºëÚ§ÙêÞ®ãÈÞ°ÙäÝ¦ãü›…š…·…Æ…Ë†M†y†ß‡«ˆ˜ŠkŠ¼ŒfŒâŽcšã“E“Ð•{Íô›K›Ä›Í¸Ûœ|œ‚~µ¹ž¿Ÿp«Y«a­˜³{³…¸f¸sºC»Ž¼t¼‡¼˜½“À€Á‡ÁŠÁÂoÅ|ÆyÈ‡ÈˆÓÖhØAØDØFÜŸÝ“ÞZâvãpäUäfåébé{é•é—ë”ëŸìô\ô„ø™üZ" },
			new String[] { "Hou", "ºóºñºðºíºîºòºïö×óóÜ©ááåËô×÷¿ðú…Ë…éˆ‹Ž«›• ê²T³@ÂFÂJÄDÈ‰ÔÚ¸Ø_àCàjãæAðfõ`ö\÷c÷ýJ" },
			new String[] {
					"Hu",
					"ºþ»§ºô»¢ºø»¥ºú»¤ºý»¡ºöºüºûºù»¦ºõÏ·ºËºÍº÷»£ðÉÙüâïð×óËìæìè÷½ä°ìïõ­çúàñìÃéõð­ìÎõúðÀâ©ã±á²äïì²ßüéÎ[‚sƒê…I…O†¼†Ø†Û‡F‡P‡©ˆ~‰Ö‰ØŠ¯Šý‹|‹¬‹­ŒŒŽŽÄuHm‘ï‘ñ‘ò‘ô‘õ’_“‡“ª”N•O•U•÷–—ü—ý˜«šXš£ãé›R›Z›~›´›üœWœXœûGžCž€ŸWŸÚ­•®@µC·‚¹}¹”ºn»‡½`½œ¿S¿T¿eÓðëÒÄŠÅnÆSÆUÂ«ÜÌ¿àÆ~ÈLÊSÊdò®ÌÌ•Î™ÐkÓ{ÔSÖ—×oÐíØmÜ à‚â’änåtåæLîÜëa¹ÍëiëŒí_í’îgðbôEô–öUö{÷s÷ŸøUø‡ù]ù–úCúKúXûI" },
			new String[] { "Hua", "»°»¨»¯»­»ª»®»¬»©»«»íîüèëæèí¹Ù¨„Øå…ÅÍÛ‡WˆµŠ£‹N‹O‹½‹ÃÑ§ŒW†ÕÒ“Š“®“çµÐ–—É˜¥˜å™Šä«±Òªœ­L®‹®“³“´hïý¼@¼AÀEÄBÅpÆ_ÈAÉJÊyÌfÌsÎ”ÓiÔ’ÕjÕ–Õ Öœ×fÝ{âDâEänåkçfò‘ô‰õqöÙú†üX" },
			new String[] { "Huai", "»µ»³»´»±»²»®õ×Ý†Fà°‡]ÛÚÅ÷‰²‰Ä‘¯‘Ñ™ÆžxÂjÌxÌ|Ñ‘Ñœ" },
			new String[] { "Huan",
					"»»»¹»½»·»¼»º»¶»Ã»Â»Á»À»¿»¸»¾äñâµß§åÕöéÛ¨÷ßå¾Û¼ïÌà÷ä¡ÝÈçÙä½†¾†¿‡È‡õÛùˆâŠJŒAŒ~`´ŽwÑ‘¤‘×“QÔ®”k—h˜¬™öšZšgš÷œo‚È×¹àžðŸ¨ íªB¬~è¥­h­’¯ˆÍîÑ£±±š²`²o¼]½b½Œ¾ÀQÁvÃKëäÇBÈPÉVËÎŒÐS×’ØhØoØ}ØŽÝkÞSß€à ãhæDèGéIêXêaëfëqóOõŒöZödøbùJûXûqðÙ" },
			new String[] { "Huang", "»Æ»Å»Î»Ä»É»Ë»Ê»Ñ»Ì»È»Ç»Ð»Í»ÏÚòëÁäêóòáåöüåØñ¥äÒó¨è«‚µƒÆ†Åˆð‰E‰ŸŠN‹hŒr¢ŽxUé“N•s•Í–M˜R˜n™¤›R›²œêžêŸºŸì pª¬‰°°Œ·k¿mÅŠÃ¢Ã£ÈÐYÔ…ÖWÖeÚ‡å–æwçuéBí‹ðcòböm÷UúŠüS" },
			new String[] {
					"Hui",
					"»Ø»á»Ò»æ»Ó»ã»Ô»Ù»Ú»Ý»Þ»Õ»Ö»à»Û»ß»×»ä»²»Ü»â»ååçä«çõÞ¥à¹í£ßÜêÍãÄ÷âÚ¶ó³Üîä§ßÔò³ÜöçÀÝƒaƒª…R…¡‡G‡j‡v‡‚‡¤‡ß‡éˆH¶é‰™‰ÄŠî‹^Œ@Œ“ŒáŽ¹@hj¡¢Úo{‘}‘Î’’“]“Ö•Ÿ•Á•þèí—Û—ò˜ž™B™b™m™u™®š§š«›i›x›‘»Áœ“œóŒÒèž`ž¾Ÿ@ŸCŸFŸŸ˜ S Zª›¬q­_­g¯`¯ð©íõî¡²N²~µ˜·xº_½}ÀDÀLÁ™ÁšÂEÂPÆUÉLÊ]ËCËDË™ÌlÌs³æÍYÍzÍ ÎšÐ„Ñ‹Ò^ÔÔœÕdÖM×M×e×f×wØYÙVÝxÝ{Î¥Þ’ßDß`ã„çiçžêTê_ëDìuíWífí}Î¤î_îœðdõtö™ýHýIÀ£" },
			new String[] { "Hun", "»ì»è»ç»ë»é»êãÔçõâÆäãÚ»‚[‚“‡õ‹Gù¸Çù»ÓÀ¦’ä“]À¥•e—p—y¹÷—•š‰›÷œ†œ¡œ³Ÿ[Ÿk¬q±d²E²J¾i¾r¾‡¿ŒçµçÅÈÊMÓoÕŸÞFé’î‚ðQðaý@" },
			new String[] { "Huo",
					"»ò»î»ð»ï»õºÍ»ñ»ö»í»ô»óàëïìñëØåÞ½ß«ïÁó¶îØâ·å‚i„Š»¯…¿…ô…ü†Ø‡—‡ÉŠ_Š£°ç’»’î“n”N”ü•ë—ë™Š›[œ­tèžCžmìáŸZ«@ð­°\±n²ˆ²‘´žµœ¶¶„·‚ºWÂhÄNÄsÅGÅŸÈuÉ^Õ’ÖfØmØ›Ô½Úoß^ß˜â€åxèZéXëbëoì[òdôr" },
			new String[] { "Hwa", "‰þ" },
			new String[] { "I", "U" },
			new String[] {
					"Ji",
					"¼¸¼°¼±¼È¼´»ú¼¦»ý¼Ç¼¶¼«¼Æ¼·¼º¼¾¼Ä¼ÍÏµ»ù¼¤¼ª¼¹¼Ê¼³¼¡¼µ¼§¼¨¼©¼¢¼£¼¬¼»¼¼¼½¼­¼¿¼À¼Á¼Â¼Ã¼®¼ÅÆÚÆäÆæ¼ÉÆë¼Ë¼Ì¼¯¸ø¸ï»÷»ø»þ¼¥»û»ü¼²ÛÔä©öÝåìì´êªöêïúí¶ð¢ê«ò±ÚµóÅôßóÇØÞß´ÝðõÒáÕÞáõÕö«ßóçÜÜùñ¤çáî¿Ø¢ÙÊÜ¸Ù¥êåé®÷ÙßÒÞªêé÷äæ÷éêá§Ø½ê÷ØÀÜÁßâMU‘³ÒÐ‚Âƒ_ƒÎ„W„Z„ˆ„©„Þ…hß²…uØÈ…¯…»…è†À†æ‡\‡ˆjˆ…ˆô‰J‰€ŠjŠ Š¸Œ¨¾ÓŒÛŒïc³ŽNŽ©Ž×Žó^åæŸ²Âî¯ê‘¢‘¼‘ÕÒ¾“V“Ä“Ø“ê“ô”D”Œ”ª”ú”û•¸•Ì–ˆ—mÆå—ù˜O˜Š˜œ˜Û™C™W™o™v™‹™›™Ãš©›D›‹½à›ùœgœ–PTçúž†Ÿd äªEâ¢­D­^­u®‚´Ã¯sñ©°U°^°n°uî¥²]öÄ´‰¶I¶S½Õ¶·I·]·b·e·m·}¹U¹œºsºuôÒ¼_¼o¼¼‰½Y½o¾@¾ƒ¿]¿ƒ¿ŽÀMÀ^½áÁYÁaÁbÂcÂfÃhÙõÃÄlÅUÅÆIÆaÆnÆˆÆ–ÇgÝ½ÈWÈ—ÉaÊDÊmÞ­ËEËj½åÌIÌRÌnÌzÌ~À¯ÎaÎŽÏlÏÏ„Ï…Ñ_ÑwÒHÒQÒˆÒ‰Ò—ÓJÓ]êèêëÓfÓsÓ‹Ó“Ó›ÕHÕ‚Õ‘×I×^Ú¦ØCØGÙ}ÙŠÚlÚ|Ú–ÛEÛaÛeÛpÛˆÛ”ÜQÜeÜuÝ‹ÞUàBàœã‚ãšåZå‰çgçˆèWèi¸ôëHëYëuë|ë}ìPìVì“íZí‡ïWï|ð‡òTóKÆïônô‚ô‡ôŠõJõŸöSöaö›÷C÷D÷q÷‚øKùHùúWúaúnûAûnýRýTýUýVýW" },
			new String[] { "Jia",
					"¼Ò¼Ó¼Ù¼Û¼Ü¼×¼Ñ¼Ð¼Î¼Ý¼Þ¼Ï¼Ô¼Õ¼Ø¼ÚÇÑ¼ÖîòÝçåÈê©ä¤ïØðèí¢áµõÊØÅÙ¤ëÎóÕçìðýÛ£ôÂòÌ‚íƒr…­ßÈ¿§†kˆ]ˆ®ÏÄ‰ìŠA‹TŒ_Ž·Ëð‘æ’S’zÑº’~Þ×êüÐ®’¶¿«“a“ü”Ï”ÐÏ¾—k—Ý˜\˜k˜–™xš¹›v›Ñ Çªmªo«w¹k¼OÂ_ÃÄ`ÇvÍÎrñÊÑWØjØ†ÙZÛOàPâ›ãeãxäeæ‰îþîRî]îaò¡ïðšñ{ñ˜Âæ÷ºø”ùGû“" },
			new String[] {
					"Jian",
					"¼û¼þ¼õ¼â¼ä¼ü¼ú¼ç¼æ½¨¼ì¼ý¼å¼ò¼ô¼ß¼à¼á¼é½¡¼è¼ö½£½¥½¦½§¼øÇ³¼ù¼ñ¼í¼ã¼ó¼î¼ï¼ð½¢¼÷¼ê¼ë½¤ôå÷µê§ÚÉêðèÅëìõÂçÌÞöê¯ë¦ÝÑöäóÈÚÙé¥àîå¿ñÐõÝíúåÀðÏÝóÙÔïµäÕü‚k‚›‚¡‚ßƒcƒ€ƒï„‡„„¦„§„ª„«º°‡ØˆÔÇµ‰A‰q‰¤Š¦Š§åîŒ{Ž¥ŽÔ”É½‘â‘ì’³’þ“B“b“ì”W”s”ð•©•üèÔ–ç—g—Ê—ß—ä˜c˜Ù˜ó˜ö™Z™z™‘™Òššž›–›×œ\œpœ—ÀÄu¾žEžRžhžˆžŒžŸÒŸæ  êùª\«l«…¬{¬‚±O²R²v²{²€´D´´–´šµM·S¹a¹{óðº]º†»E»W¼G½€¾}¿V¿ ÀOÀoÀwÏËÂžÅ[ÅžÆDÝ¢ÈGÈ‚È…È“Ê`ÊzÊ—Ë]ËuÌ‚ÏMÏ•ÑIÒMÒOÒ}ÒŠÓSÓVÔdÕÖGÖˆ×P×t×vÚÚØ]ØbÙ`ÙvÚ{Ú™Û`ÞYá_ázâJâVâ]ã‹äEä[ägä’åXåbåså€æGæIæ~çZç‰ç™èBèaèbè{è~èƒè—Ç®éfégÏÕëUìyíKí[ídðTðeñJòqôCöröxööž÷œøZùpúYûxûyû{û|û…übüjÛÈ" },
			new String[] { "Jiang", "½«½²½­½±½µ½¬½©½ª½´½¯½®½³Ç¿½°ºçôøíäçÖêññðç­ÜüôÝä®‚×„ß…G‰D‰¬‰áŠXŠ\Œ¢Úx‰Š™“°“À–t˜ª™^™ºš™@{ª„®{®–®Ÿ¼T¼t½{ÀPºìÁžÄvÈwÊ@ÊYËKÎ…ÏQÑHÖvÖ˜ánáuí\îŽ÷F÷š" },
			new String[] {
					"Jiao",
					"½Ð½Å½»½Ç½Ì½Ï½É¾õ½¹½º½¿½ÊÐ£½Á½¾½Æ½½½Ã½¼½À½¶½Î½Ñ½·½¸½È½Â½Í½Ä½ËáèÜ´ÙÕòÔë¸á½õÓæ¯ð¨ÜúðÔàÝõ´Ù®äÐöÞÞØÇÇÇÈƒSƒeƒ‚„¤„à„äÈ´…s…ÓÒ§†Ì†û†ý‡E‡U‡„Ñý‹´‹É‹Ð‹ùÑ§ŒWjÛõþŽBkˆ‘x‘‹‘¢’›’¹“¼“×“è”‡”œ”©”º”¼”Ò•w•¯•Ý˜È˜ò™Ëœ©œò]‰²¼¤ž•ž«žìŸ}Ÿ”Ÿ÷ª—«„­d°‰°³C·X·p·•¸‹¹RºŠ¼m½gÀUÀq¾ÀÄ_ÄzÄ‰ÅTÆLÆ›ÝÄÈcÊwËŠÌ—ÏfÏtÒÒ™ÓXÓŠ×K×_Ù]ÚˆÚŠÛ]ÜFÝ^ÞBÞIàzá†á ãqç€ïœòœófõo÷RøŸùaú„úŒæù" },
			new String[] {
					"Jie",
					"½Ó½Ú½Ö½è½Ô½Ø½â½ç½á½ì½ã½Ò½ä½é½×½Ù½æ½ß½à½ê½å¼Û¿¬½Õ½Û½Ü½Ý½ë½Þ¼ÒÙÊèîà®Þ×÷ºôÉò»àµò¡öÚæ¼íÙÚ¦æÝðÜÚµNº¥‚Œ¼Ù‚Í‚Üƒr„f„g„o„Â…m…Ãßó†‡†—‡»øˆêˆûÆõËýŠo‹d‹m‹}‹‘Œ¨ŒÃŒîŒô›ºËŽOŽYŽ^ŽÑŽàÈð…’Mµ£Ê°’÷“ƒ“ø”O”T”â•Môß•Ì—A—¸—ô˜H˜P¸Å˜m˜‹™w™Ãš²›­œf¿ÊœœœïŸ® ÏªEâ³«d¬p®v¯C¯^°X³VíÀµ@×æ·M¹¼®¼v¼½Y½eÀTæüÂcÃÅ‹Ç}ÇÉ•Í„ÍŽÍÎaÎfÏ˜ÏÐVÐwÐ|ÐñÊÑKÑ\Ñ›ÓnÓ“Ô‘Õ]ÕmÖŠÚlÛOÛdÞ—ã]å|æOïÇëAëeìŒì“îRï÷Øô‚ôõ^ù™Úà" },
			new String[] { "Jin",
					"½ø½ü½ñ½ö½ô½ð½ï¾¡¾¢½û½þ½õ½ú½î½ò½÷½í½ó½ý½ùâÛèªâËéÈñÆÝÀÝ£ñæàäçÆÚáæ¡êáêîµ‚BƒHƒqƒƒ»„B„³„Å…Ò÷†‚‡žˆ²ˆü‰ƒ‰½Šú‹¦‹Í‹âŒƒûŽ„®‘[“|”Ü•x–‡˜cšVš›»œÃWäøŸ¥ a«ƒ¬Q¬n¬’­\­n±M³\µ‰¸…¸’¼Ž½G¾o¿NÀßÅ]ÇMÇžÈBÉ“Ë|ÓPÓbÔCÖ”ÙÚBßMáŽâYäuå\îÄï·ð~ñ^ûvüTý„" },
			new String[] {
					"Jing",
					"¾¹¾²¾®¾ª¾­¾µ¾©¾»¾´¾«¾°¾¯¾º¾³¾¶¾£¾§¾¨¾¬¾±¾¤¾¥¾¦¾¢¾·¾¸ëÂâ°ÚåëæåòØÙã½æºëÖÝ¼ÙÓìºåÉö¦ãþSŠ¤‚\‚Š‚ýƒ ƒô„q„³„ÅˆgˆiˆlŠnŠøŠùŒcŽyŽÁ†½‘ “÷”ìêÉ•Ç•ß—J—}™YéÑš„ÊÏ›G›H›·›ÜœQžDžsÌþŸN GªS«E­E­Z­`¯d¶p¶“·¸t¸x¸‚¸„óä»~½U½›Â€Ã„ÇGÇoÈòßÏ‚ÕeÛVÞŸÐÑätçRÚêê€ìiÇàìmìnìoîKîeîiïIó@öLùXù~ù‚ûû—ü " },
			new String[] { "Jiong", "¾½¾¼ìçåÄ‚CƒTØçƒÕƒ×‡åˆsÛðˆ·Œl‘û•Q›s›Ó°ž]êÁŸKŸ Ÿ¡ŸÉŸâŸü E½N½ŸÅQÅSÌSÌWÑ•Þ›ã}ævîyïGñoñ’" },
			new String[] { "Jiu", "¾Í¾Å¾Æ¾É¾Ã¾¾¾È¾À¾Ë¾¿¾Â¾Ç¾Ê¾Á¾Ä¾Ì¾ÎôñðÕõíÙÖèÑèê÷Ýð¯ãÎà±LX`‚w„—„ó…B…E…YàÝŠeŽýGH³î‘W’º“A“[“š–`–w–Í˜Í˜þš”šð›CäÐœ© ¬®o·T·c·•¼j¼m¼‘¿ŠçÑÅfÅiÈ\òøéNíƒôböJøFúûýn" },
			new String[] { "Jou", "™ã" },
			new String[] {
					"Ju",
					"¾ä¾Ù¾Þ¾Ö¾ß¾à¾â¾ç¾Ó¾Û¾Ð¾Õ¾Ø¾Ú¾Ü¾å¾Ï¾Ñ¾ÔÇÒ¾Ý¹ñ½Û¾ã³µ¾×¾Ò¾á¾æÙÆõ¶ñÕåðêøÜÚñÀì«ï¸é§ÜÄè¢Þäé°ö´ôòÜìöÂåáéÙõáé·÷¶îÒÚªèÛ³ð¼Øþ‚I‚e‚˜ƒhƒâ„H„¡„è„û¸æˆRˆoˆ¿ˆÏ‰±½ãŠŠÛÈ¢Šè‹JŒŠŒÕŒøŒþ‡ŽelßþDIì‘§‘Ö’]’‡¹°’¤’±’º“E“T“þ”H•Z—x—º—»™h™Î™ÛšjšÁšÆ›t›†›®›ôœHÇþœ¦ÞŸhŸq „ Ê ó«~¯Y±röÄ³^×â¶€¸M¹_Â¨ºtº–»c»‰»ÁDÂ`Â‹ÄKÄ”ÅeÅ‰ÝÏÈgÈ{ÝäÉXÉaÉ›ÊVÌ^Þ¾ÌŽÌ˜ÍiÇùÎAÏJÐÒzÔnÕ‡ØeØ‹ÚkÚzÚ~×ãÚ ÛBÛRÛgÛžÜFÜMÜvÝ@Ý]ßš×ÞàTàYà`àuÛ¸â ãIäzä|äèL³úé…ê³ûërïZñuñxñòœóM½¾å÷õLõQõX÷‰ø~ùVùqù‰úGüŸýAýe" },
			new String[] { "Juan", "¾íÈ¦¾ë¾é¾è¾ê¾ì¾îÛ²ïÃîÃïÔáúèðä¸öÁ„»„Ì„æ…Û‡üˆ±ˆ»ŠFŠ¤ŽQŽ`Ž™ƒ€³‘g’Ôß§–K—]—¨ãù›ûŸ]ª™®CÕçÑ£íü±’²C½v½¿xÁIÁ\ÃÄCÄ–ÇšÈTÊ^ÈïÊtòéÑZÒN×zÛmÉíägämäŸæŒçé—ëhëvìœíjï…ðCùJùN" },
			new String[] {
					"Jue",
					"¾ö¾ø¾õ½Ç¾ô¾ò¾÷¾ï¾ó¾ñ¾ð½À½ÅèöàåéÓàµõûØãìßÛÇïãâ±çåáÈÞ§àÙÚÜõêæÞØÊÒÒ|‚à„]„ä…Z…¨…É†­ˆ«‰®‰øŠxŒHÇüŒÖŒØŽ@ŽD{ž‘‰‘•‘Ý’¢’Á“Þ”Çèß™@™ê™þš€šÜ›Q›‰ˆžŸŸ]Ÿ}ŸØŸ÷ u “¿ñ«P«i«k¬œ­W¯N¯‹²œ²Ÿ³O·‡Ñ¨½^½~¿”¿›Ä_Ä”Æ`Ê…Þ©ÍDÍXÍÜÏpÏqó½ÐœÒÒ™ÓXÓÔEÕo×HØÚbÚkÚ‘ÜBÜFÜjÝ^½ÏßIâfç~çè‘éQé êIã×ãÚ÷³íXñiòjóYóp÷Z÷¬ø_ø`ùŠú€ý™" },
			new String[] { "Jun", "¾ü¾ý¾ù¾ú¿¡¾þ¹ê¿¢¿¥¾û¿£¿¤óÞ÷åñäÞÜƒy„òÔÈ…Íˆ­Š®Œ”‘®”hÑ®•€—T›JžFŸaŸlŸóâ¡¬B®°—°˜´A¹„¹‰ÇqÈšÊ^ÍSÎDÐ‚ÒŸÙêÜŠâxãzã—ä]å‹ê}öÁëhðKðžòEõz÷ùQùRùUûŠûŽý”" },
			new String[] { "Ka", "¿¨¿¦¿©¿§ëÌßÇØû…íˆšçìÐ_ÑQãl" },
			new String[] { "Kai", "¿ª¿«¿­¿®¿¬ÛîØÜï´îøïÇâéâýÝÜ„P„’ÛÀ„ÑºÈ†Ë†þ‡i‰NŠKÌŽ¯Ôð÷•°ºË™üš@œf¿ÊäÛžGžÍžÏžýÐ_ØMÝaå|æbæzç˜é_êGê]ïôïa" },
			new String[] { "Kal", "f" },
			new String[] { "Kan", "¿´¿³¿°¿¯Ç¶¿²¼÷¿±íèê¬Ù©î«Ý¨ãÛ‚°ƒÝÛÉº°ˆÉ‰A‰d‰{€®§’X–Ý™‘šKšM±O²™´U´|¸ƒÝ²ËWÐb×tÝ|ÝÝÞRêRìyîƒðWÏÚý" },
			new String[] { "Kang", "¿¹¿»¿¸¿·¿µ¿¶¿ºîÖãÊØø…H‡ã¿Ó·Ü‹¢Üý“•º¼˜±ãìo è³T·^»~»ÄÜ{ß’â‚ç_é`êlóa÷K" },
			new String[] { "Kao", "¿¿¿¼¿¾¿½èàêûåêîí@Ïì°ÞØ¸ã“×”Ž˜‚éÂ›ŸŸ\ŸÀ _·XË^Ë›ÓˆäDó}õwõ‘÷Š" },
			new String[] { "Ke",
					"¿È¿É¿Ë¿Ã¿Æ¿Å¿Ì¿Î¿Í¿Ç¿Ê¿Á¿Â¿Ä¿ÀºÇã¡á³òòç¼òÂéðñ½îÝë´ò¤î§ï¾ïýçæ÷Áðâà¾äÛæì„w„Ä„Ë…\¿¦ˆÑŠÄŒ¡QºÁŽP…ÙÚ“U“t”¨˜}˜Ê™üšMš£šÎœfžGžÜ ˜ É¯zîÁ³`³‚íÙ´R´h´žµL¸Sºœ¾~Á˜ÃmÅ‹ÈdËPÐ_ÐŽÕnÚÝVáfâŽãxä˜åHîþï¹îWîwòSý" },
			new String[] { "Kei", "¿Ì„w„Ä„ËŒ¡" },
			new String[] { "Ken", "¿Ï¿Ð¿Ò¿ÑñÌÛó‰¨‘©’õºÝ«³wÃGÃ\ÑyØcØ~åoí ñýýlö¸" },
			new String[] { "Keng", "¿Ó¿Ôï¬„´ˆcŠRŠsìþ’®“@“¾š  ¾³n³wíÊ³³™ëÖÃ„ÕUÛVãsäLå”çHîïêl" },
			new String[] { "Ki", "]" },
			new String[] { "Kong", "¿Õ¿×¿Ø¿ÖÙÅáÇóíˆÂŒ^£—¾›ï³M³œñ·ÁzÇ»ÜwåIìùy" },
			new String[] { "Kos", "W" },
			new String[] { "Kou", "¿Ú¿Û¿Ù¿ÜÞ¢ÜÒíîóØßµØþƒã„›„¼åý‹žŒt“D¿æ“¸””šªœÏA±r²]²g·¸lºpÆ’Êfâ@æ–úd" },
			new String[] { "Ku", "¿Þ¿â¿à¿Ý¿ã¿ß¿áØÚ÷¼à·Ü¥ç«‚V¹Å‡¿‡ýÊ¥Žì’H¿æ’¹¾ò“‡–F–ö—ü›{Ÿ\ª@¯‰³L³‚¶s·”½fÃdÑFÑÚÚœ¿çß õpýJ" },
			new String[] { "Kua", "¿ç¿å¿æ¿ä¿èÙ¨†EŠ¯m•v—ë½\Å~ÈAÊyÐŽÕFã’ä˜ï¾îóg÷Á" },
			new String[] { "Kuai", "¿ì¿é¿ê»á¿ëßàØáä«Û¦áöëÚ¿þƒ~„S‡ˆˆQ‰K‰‘ŽwX“ù”÷•þÒªœ­g¼[Ä’ÝÞÊ‰à”ñiôU¿ý÷d÷Ž" },
			new String[] { "Kuan", "¿í¿î÷ÅÍêŒˆŒ’—p¿ÃšEšL¸T¸Uèwîw¿Åóy" },
			new String[] { "Kuang", "¿ó¿ð¿ñ¿ò¿ö¿õ¿ï¿ôÚ¿Ú÷æþÞÅÚ²ÛÛêÜßÑOƒ—ÐÖ„Á…N…j‰¿DVûb‘Ç‘È’[•p•çÍ÷›r›¬äÒ p ï±q²Ž³m³q»ÇµV·ƒ¹n½T½_ÀkÕEÕNÙLÜ’ÜœÝAÝHÞ‚¹äßà—ãkäqèkù\üY" },
			new String[] { "Kui",
					"¿÷À¢¿ü¿ú¿û¿ýÀ¡¿ø¿þ¿ùØÑã´Þñî¥õÍñùóñà­åÓêÒÝÞã¦à°Ø¸òñÚóÙç„l…T…t‡]‹‹ÅŒºŽhŽu‘|‘è•u—ó—õ˜æ™œšCšwš•¢Ÿ²z´j¸Qºˆ»AÈ±ÂÂ‘Â˜ÃvÄCÄ„ô§ÉJÊ‰ËwÌlÌwÌ€ÌõûÖd²ÈÛ“ÜiàkåžæKçqè^é êNã×í•í–íŸî`îÇêðrðòjóYÀ£" },
			new String[] { "Kun", "À¦À§À¥À¤öïï¿÷Õçûõ«ãÍã§ÂÑˆÒˆÜ‰×‰Ú‹GŒ±Š‹•‚—y›Ù»ì„ŸjŸãª^­@±—³µŒ¶‘¶Ÿ½™ÁHÅCÇÎJÑTÑXÑhÑ‚ØcØ~åKé€éîBÍçðQâÆòOó‚óˆöHöŠ÷¤ù{úAûdýlö¸" },
			new String[] { "Kuo", "À«À©ÀªÊÊòÒèé»áØÚßà‡p‡ˆ‘²’ˆ’•”U•þ—ItžN T ‰¶„¹QÈuÈvÚ÷à—éŸìHíAíTípîSó–ôUÀ¨" },
			new String[] { "Kweok", "·i" },
			new String[] { "Kwi", "™Í" },
			new String[] { "La", "À­À²À±À¯À°À®À¬À¶ÂäðøååíÇØÝê¹ƒ•‡Ä“X“Y“yß¡”Y”j–¬—ï™Êœ¼ m­†°]´rÁÄ—ÅDÇ‰ËˆÎ`Î|ÏžÞhènéJíBôFö_÷vñ®" },
			new String[] { "Lai", "À´ÀµÀ³äþêãáÁäµïªô¥áâíùí‚g‚|„Ð…–†‹ŠÅ‹@ˆŽòÆ‘Ð”j—…—®™ÊœZž|žª[¬[°]²A¹X¹s»[ÈRÌDÒsÕvÙlÙ‡ßFà[áånîmîsòQöDù`ù„üH÷óñ®" },
			new String[] { "Lan",
					"À¶À¼ÀÃÀ¹ÀºÀÁÀ¸À¿ÀÂÀÄÀ»À¾À·À½ÀÀé­á°ñÜïçìµî½äíƒNƒ‹…•ßø‡•‡Ûˆh‰°‰·‹ö‹ûŒG¹ŽÓ[âÞ°ãÁ‘¾‘Ð”G”r”ˆ”Ì•©™Ú™ì™í›ÇÁ°œ‹ižEž‘ž™ž°ž±Á¶Ÿ’ A L f € ˆ Š­s­Šµf»@»_¼hÀaÀ|ÈŸË{ÌkÌmÒ[ÒhÒwÒ€ÓEÓ[ÖG×E×ŽÚÉÜ_³»áYè|è”ê@íeïC" },
			new String[] { "Lang", "ÀËÀÇÀÈÀÉÀÊÀÆÀÅïüòëÝ¹à¥ï¶ãÏÝõ‚Z„É†]†}ˆ°‰i‹™~”–J–T—O˜¸˜ÑšDŸR¬˜³„¹^Á}¸þÃžÅ…ÉvÉ‡Í™ÕLõÔÜqàHàOäZæƒéò@" },
			new String[] { "Lao", "ÀÏÀÌÀÎÀÍÀÓÀÔÂäÀÑÀÒÂçÀÐÁÊñìï©õ²îîßëèááÀðìƒXÁÅ„º„Ú†K†[†ë‡E‡Z‹ª÷`‘Ž‘“ÆÁÃ–U˜÷™Q›Ð³ªJâ²«™°A³z´‹·ºŒ»”½jÂgÇNÞ¤ÍŒÏoÜ~ÞLã™ç„î‘ó€õu" },
			new String[] { "Le", "ÁËÀÖÀÕÀß÷¦Øìß·ãîàÏà’A˜S˜·ší ¬«W³i¸…º{ÆIêbí‰ðEð›ö˜" },
			new String[] { "Lei", "ÀàÀÛÀáÀ×ÀÝÀÕÀÞÀÙÀßÀØÀÜÀÚçÐÚ³ñçõªÙúæÐéÛàÏ‚ñƒ±Â¬…ŸßÖ‰C‰¾‰Í”b˜Ã™¦™§™ï›¤›æœIäðž˜­z®š¯°N±R²´´ µWµXµˆ¶a½t¿wÀhÀnÀ}ÀœÃšÄBÉ ÊuË‰ÌqÌrÌ{Ì…ÏœÕC×|Þ[à[ãåGèDèhèˆìYîLî[îïK÷mûPýF" },
			new String[] {
					"Li",
					"ÀïÀëÁ¦Á¢ÀîÀýÁ¨ÀíÀûÀæÀåÀñÀúÀöÀôÀùÀìÀòÀüÀóÀþÁ¡ÀêÁ£Á¤Á¥ÀõÁ§ÀðÀ÷ÀøÀçÀèÀéÛªð¿óÒÛÞÜÂ÷¯çÊõÈòÛï®ã¦å¢ôÏÝñèÀó»Øªß¿íÂæËóöðÝðßáûÞ¼äàöâìåèÝà¦õ·éöîºæêî¾åÎÙ³à¬ö¨÷óÝ°ÙµòÃæ²ØìÎ»ƒ¢ƒ«ƒú„^ÁÐ„{„˜„°„î……“…–…«…¬ß·†o‡­‡³‡Î‡Ñ‰W‰ÈŠÚ‹KŒCŒVŒÞŒübqŽ_c“—Ÿü’A’FÞæ“…”^”i”ƒ”‰”Á•·•Ñ•å–^–ª–Ð–Û–ï—~—ˆ—˜˜»™‚™ª™µ™À™æ™ðšsšvšÓ›l›mÆü›É›ãœIžTžWžrž¢ž¦ i s À Ó Ø«†çó¬P¬—­|­€­‰­–°O°[°±L±X²@²—³PíÇíÑ´•µZµ[µ`¶Y¶]¶w·ˆ¸{¹]»h»Œ»š¼H¼c¾F¿rÀfÀ{ôçÃšÅƒÆnÆÇVÇ—ÉTÉWÊkËžÌyÍjÍÎGÎgÎ€Ï[Ï~Ï‹Ï ÐGÑYÑeÑŸÓ€Ö‚×ØNØ‚Ú\ÜVÞ]Þ^ß†ßŠáBárá‡áãWã‰ä‚ä‡äœåGækç\ègèpîåïÓë_ë`ëxö²ìZìcïSìªóPóœôfõŽõ”öP÷k÷u÷w÷~øEøtùvúbûZûû•" },
			new String[] { "Lia", "Á©‚z" },
			new String[] { "Lian",
					"Á¬ÁªÁ·Á«ÁµÁ³Á¶Á´Á²Á¯Á®Á±Á­Á°ó¹çöéçÝüöãÞÆäòì¡ñÍå¥ñÏé¬ŽÁîƒI„ …U…V†ö‡tˆäŠYæ®‹t‹¼‹ÕŒD‘X‘z‘ÙÂÎÞö“¢“ì””¿—†˜˜™¹™Úšašš›Ëœ‹œÇiÔïž‡Ÿ’ŸÈ R¬…­Iî¬´n·SºŸ»^»d¾š¿€À~Á„ÙúÂIÂŽÂÂ’Â“ì¢Ä˜ÜßÝ²ÈjÉËOËWÌ_Ì`Î‹ÑžÒcÒœÖ‹×`ÛšßBà˜ázåbå€æ`æœç ïçÁãôHönö–÷H" },
			new String[] { "Liang", "Á½ÁÁÁ¾Á¹Á¸ÁºÁ¿Á¼ÁÀÁÂÁ©Á»Ü®ö¦õÔé£÷ËÝ¹I‚Z‚z‚ŠƒÉ†]†|†¤†È”¾ª’ë˜Å›öœ´Ÿ´º|¼Z¾H¾nÃžÍ™ÎWÑoÕõçÛ˜ÝgÝvÝˆÞcåyéãÏìnò@ôuÙû" },
			new String[] { "Liao", "ÁËÁÏÁÃÁÄÁÌÁÆÁÎÁÇÁÉÁÅÁÈÁÍÁÊîÉÞ¤ÞÍå¼çÔâ²ðÓàÚÀÐƒJÀÍ„Ú‹»Œ®Œ³Œ×Ûùú\‘l‘’“š”¶•Å˜ÍxžÒ r v­V¯Ÿ²t¸N¸Xºƒ¿ÄkÄ‚ÏYÏiÏoØIÙ’ÛŽÜGÞLß|à€á‘çBç‚éHéRïfïmósú" },
			new String[] { "Lie", "ÁÐÁÑÁÔÁÓÁÒßÖÛøÞæ÷àôóõñÙýä£Àý‚|ƒ•„µ„Ã†`ˆ´ŠGŠ²hŽ_Ž{è’ž’£”YÀõ—˜™§š¸›¼ŸIŸ­ M i m Úªd«C±Ÿ¾FÂ~Ã‡²²ÅDÆ”Í}Þ˜ååïVôQõh÷vø•" },
			new String[] { "Lin",
					"ÁÖÁÙÁÜÁÚÁ×ÁÛÁÞÁßÁàÁÕÁØÁÝåàá×ÝþôÔ÷ëõïê¥âÞãÁî¬éÝì¢ßøzÈÎ²ƒj„C…›‰É…[t‡°‘¬“Ô”Ý•—•É˜ð™_›ØÉøB«ÅžŠŸiŸûª«l­U®V®ž¯r°R°S´@·A¹ƒ»‘¿šÂLÅRÈHÌAÙUÜCÜ\ÜkÞOÞ`àçléŠÒõëOïCò•õC÷[û‹" },
			new String[] { "Ling",
					"ÁíÁîÁìÁãÁåÁáÁéÁëÁäÁèÁêÁâÁæÁçÀâôáòÈÜßç±ê²Û¹ßÊãöèùèÚöìñöàò‚’Àä„cˆ{‰çŠ–Šê‹øH’ŽX¶Á¯Áà’è•`–E™Ð™ôœRÎžƒžâ U ‹ ÷¬O°s³gÁ×µ’¶{¸n¸ ½@¾cÅzÉˆÊCÊ™ÌhÐeÐ‡ÑkÔfÚšÝCÝsáá”âä™¶¤éqêtë‘ë™ëëžì_ì`îIñ|õCöNøoû_ûwû™ýgýhý’" },
			new String[] { "Liu",
					"ÁùÁ÷ÁôÁõÁøÁïÁòÁöÁñÁðÁóÂµÂ½ç¸ï³öÌïÖä¯æòì¼ðÒìÖåÞÙÍƒE„¢‡®‰g‹ˆ‹ôÍA‘ËÂÕ”å”é–Î—B—P˜ñ™P›fã÷±ÃÓÎx¸žgŸÞ«€¬Š¬–­]®o®q®‘®œ°@´e´z¾^ÁSÁ[Á’ÁÄÄ|ÝäÉ]ÉsÞ¤ÊVË˜ÏYÑ^Û‰ãTäæyçBçsÃ­éHê‘ëwìCïdïfïiïvðsñ‡ñœòtò˜ôjö†úVúwûmûˆ" },
			new String[] { "Lo", "¿©‡Þ" },
			new String[] { "Long", "ÁúÂ£ÁýÁûÂ¡Â¢ÅªÁüÁþÂ¤ÛâëÊççÜ×ãñèÐñªíÃƒ¥…€†U‡µ‰Å‰Æ³èŒ™ŒâðŽaŽbÅÓÜ×Ü”n•o•î–V—Y˜™™Éœ¬œöVž{ z­‡±€²”³Šµaµb¸_¸oºTº\»\Ã@Ê”ÌdÎgÐFÐHÐiÒtØFØLÚLÜ[çXèxë]ìNì_óGûTýˆý‹ýýŽ" },
			new String[] { "Lou", "Â¥Â§Â©ÂªÂ¶Â¦Â¨ÙÍò÷ïÎÝäñï÷Ãà¶ðüáÐƒE‡D‰vŠäŒŠŒÍâI‘f“§˜Çœ¾UŸÓÀÎ®R¯›¯œ²k¸MºtÂeÄ|Å”ÊVÏNÖŒÜ}ßsçUíVót" },
			new String[] {
					"Lu",
					"Â·Â¶Â¼Â¹Â½Â¯Â¬Â³Â±Â«Â­Â®ÂµÂ°ÂÌÂ²Â¸Â¾ÂºÂ»Â´ÁùöÔèÓäËÞ¤åÖãòéñëªóüéÖéûÛäëÍààïåê¤äõß£è´ðµðØôµôƒJ„—„Î„Û†ë‡£‡´ˆPˆv‰n‰o‰ÀŠáVL] ’ ÞÛ’ÇÞó“¦“ï”]”d”m–›—¶˜Ä˜Ì™©™¾šÚœGœOÂËFUÂžVžZžoŸÑ t«G«S¬f­o­ˆ®fñ¬±J±R²F²’³t³”´{µ“¶˜·c¹‚ºŽº—º˜»U»V»œ¾vÀrÀžÁ’·ôÄrÄwÄyÅFÅyÅ›Æ@ÆAÇŠÉÊIÊ€ÌJÌÌ”ÏFÐB½ÇÓtÓ€¹ÈÙTÚ€ÛjÛÝ`ÞAÞ_à~ÀÒáXâ„ä›åhåjæ”çGçeçœèuèzê‘ïBòJòƒózô”ô—öI÷w÷|øšùcùnú˜ûRûuüu" },
			new String[] { "Luan", "ÂÒÂÑÂÐÂÍÂÏÂÎèïöÇÙõæ®ð½vyˆJˆKŠaŒDŒ\Žn”•ð™èž¤ž´°f°gÁcÃ‡ÅLÅMËHÌ‰ÓTá›èŽùFû[" },
			new String[] { "Lue", "ÂÔÂÓï²„…ˆGŒœ”^ÂÊ®ˆ·DÒ©ËŽÔ›äsäx" },
			new String[] { "Lun", "ÂÛÂÖÂÕÂ×ÂÙÂØÂÚàðö‚‡÷ˆÀ‹E‘¥’à—‹œSœÓ±š´K´ˆ¶—¾]ÂbÄ@Ç’ÎFÕ“ÛiÝ†ä—êöM" },
			new String[] { "Luo",
					"ÂäÂÞÂàÂãÂâÀÓÂáÂÝÂÜÂåÂæÂßÂç¿©ÜýäðÙùöÃÙÀíÑé¡ÞÛëáñ§ÞûãøçóïÝâ¤ÀÖƒ¬ƒ±„s†ª‡ÓR’µ’Ó”m”{”‰•ï¹û¸ñ˜·éÒ™µ™å™ïžTË¸ q Îâ£«M°e°³`³ŠÂµµ[¹J»j½jÀzÁ_ÄTÅIÆŒÉzÌ}ÍxòäÎó»ñËÓTÓZÔ›õÈÂ·ÜVÜsß‰ãtæƒæ èŒ¸õîbð”ñ˜ò…òŸõi÷wùBúŸ" },
			new String[] { "Lv",
					"ÂÌÂÊÂÁÂ¿ÂÃÂÅÂËÂÀÂÉÂÈÂÆÂÂÂÇÂÄÙÍëöéµãÌÞÛñÚïù‚Hƒ–„í…iÂ¬…Î†`ˆ‡‰¾Â¦ŠäŒŠŒœŒÒ¯Â®]‘]‘f•ì—o˜Ç™¬™°™¾šÑŸf l¯œ±R²kµ~·t·„¹˜Â¨ºtÀÛ½…¾G¾v¿|¿†ÄoÄ|È„ÝäÊVËƒÒ@ÖŒÜ}àLäXåhçUèrïÎé‚ñeóHô”Â³úyÂ¹" },
			new String[] { "M", "ß¼…Þ‡`" },
			new String[] { "Ma", "ÂðÂèÂíÂïÂéÂîÄ¨ÂëÂêÂìÄ¦ßéó¡áïæÖè¿Ã´}‚ØÄÅ†á‡O‹Œ‹°‹ßŒIŒ­˜qœÔ Ðªw¬”¯q²K´aµTµl¶MÁRÝëÊhÎ›ÏWºÑõößjæ‹éUÃÒñRñˆò‡ôKö‡úiüN÷á" },
			new String[] { "Mai", "ÂòÂôÂõÂñÂóÂöÛ½ö²Ý¤Ï„êßäÁ¨‡X‰ÓÅÉÃ}Ê{ËhÐ]Ø‚ÙIÙuß~ì@ìAú”ûœßé" },
			new String[] { "Man", "ÂúÂýÂ÷ÂþÂùÂûÂüÂøÂñÃ¡á£÷©Ü¬òýïÜò©÷´çÏì×ƒKŠ›‹ ŒÌÄ»Ž‘`“¶˜´˜ÑœºMÏÙªƒ±”²m²–½ƒ¿zÊAÌpÏTÏ\ÐUÒZÖ™õçÛ˜à„æžçNî”ðzôMôNö " },
			new String[] { "Mang", "Ã¦Ã¢Ã¤Ã§Ã£Ã¥íËÚøòþäÝ………¹†W‰ÜŠÁŒ´Ží}–Mëü–n–xšû›À ¯ ½ªK®m¯g±ZÞ«³‰¸ˆÆŸÇƒÌMÍ{Ï‘âIä€èšñ ûLûsýˆýÁú" },
			new String[] { "Mao", "Ã«Ã°Ã±Ã¨Ã¬Ã®Ã²Ã¯Ã³Ã­ÃªÃ©ë£Üâè£òú÷Öí®êÄêóî¦á¹Ùóó±ì¸ã÷Ù°ƒÐƒÓƒØÛÃ„Õˆé‰î‹u¶Ø‘ùÃè•§–‰—ûš»šÊšÓœ~Ä² Óª…°p±g¶m¹F¾ˆÁEºÄÆdÈrÉ‹òÖÎcÒ‘Ø~ØˆÙQÜšà|àŽáFãTãwå^ìWóùš" },
			new String[] { "Me", "Ã´‡¡‡ª‡¼ŒPŽÛžQ°ZüN÷á" },
			new String[] { "Mei",
					"Ã»Ã¿ÃºÃ¾ÃÀÃ¸ÃÃÃ¶Ã¹ÃµÃ¼Ã·ÃÂÃÁÃ½ÃÓÃÄÃÕÄ­áÒâ­ñÇäØä¼ðÌÝ®÷ÈïÑé¹ƒñ…ÐÎ¶‡ªˆbÛéˆõ‰rÄ«‹Z‹‰‹Ê±Û’{’¯”u”|Ä³–Ï˜M˜Ž™­š°šî›]›iœ„œŽœÕŸ¢ B¬C¬s¯c±g±t±Œ²S²‚µ|¶C¹ŸÁoÃzÃŠÄPÄŠÆ€ÉBÌjÎnÚ›ÜzàdäYæVæ[íiômúBüeüq" },
			new String[] { "Men", "ÃÅÃÇÃÆí¯ÞÑîÍìË‚ƒBŽž¸‘¿’Ð•¹—Èš‰ãëœºÂúM Fçä«f«j­J²m·`Ç–ÌŠå{éTéY÷´" },
			new String[] { "Meng", "ÃÍÃÎÃÉÃÌÃÏÃËÃÊÃÈÃ¥íæòìÛÂãÂÝùó·òµëüô»ô¿Þ«ƒƒá‰ô‰õ‹“Œ´ŽÌŽí‘¸‘º’ú”BÃ÷•ä˜ýšÙœÉ÷«B®H®mî¨²‰²“ÁEÇmÈ_ÊpÎ{ó±òþà‘à–äYåië‰ëœìDìFìWìXîŸðóõ’öQ÷jûLûsüwö¼ü€" },
			new String[] { "Meo", "Û_" },
			new String[] { "Mi",
					"Ã×ÃÜÃÔÃÐÃÛÃÕÃÙÃØÃÖÃÝÃÒÃÓÃÚÃÑÞÂ÷ãßäãè÷çìòâ¨åôÚ×ØÂëßåµôÍà×ôéñÚ¢ƒßƒç„¯†O‰QŒBŒsŒ©Œª¶ûaŽ¶ŽÈçÛ›‘ÛÄ¦“º”C”V”}˜a˜Æ™™—›^›m›¦œPœ}äéDeðóž…ž§ŸÇ † –â´«J­Œ±~±‰²[²yµz¶[·`º€»H»…ÁAÁ]ÁdÆƒÈŽÉoÉqÊUÊZËzÎ^ÑAÒ’Ò“ÒšÔ™ÖiÖk±Ùá‚áƒáˆãèféSûJû†û”ü†" },
			new String[] { "Mian", "ÃæÃÞÃâÃàÃßÃåÃãÃáÃäëïäÏííãæö¼äÅD‚a‚ÁÚ¤„Ò…›†»Šå‹i‹îÒ™†™¡šóãýœ¡Æ ¤î¨²Š²Œ²¼E½ƒ¾d¾r¾‚¾‡¾’çÅÅXÆPÇ|ÈxÎeÏŸìrìtõ|û ü@üIüMüwå²" },
			new String[] { "Miao", "ÃëÃçÃíÃîÃèÃéÃêÃìíðçÑç¿íµß÷èÂðÅåã£³³‹b‹·ŽøR®Ã¨«Q¸kºF¼†¾ˆ¾˜É´òçÔN÷]ù‘" },
			new String[] { "Mie", "ÃðÃïßãóúóºØ¿…¸ßä†_ŒPŽÏ‘Ì“}™­œçžf±uËIÐ`Ò”ÚÅèf÷xøp" },
			new String[] { "Min", "ÃñÃòÃôÃöÃóÃõçëíªçÅãÉçäÜåãýö¼÷ªáºƒoƒäƒí„b„Ç…Ýˆ„Š“‰ëB‘O‘‘’Ï”•”°•F•G•¡ãëœbœ“œ¡˜¬Y¬\¬z¯x±]±aÃß³R´C¸œ¹Iº‡¾r¾‡ÀKÉþÁFÏŸÙ‚âŒä æFéhé}öšøsüw" },
			new String[] { "Ming", "ÃûÃ÷ÃüÃùÃúÃøÃËÚ¤î¨êÔÜøäéõ¤âƒüŠ±‹“‘D’ø–L˜i›³ªu±b±…ÃÈÉqÓKÔšàpã‘øQ" },
			new String[] { "Miu", "ÃýçÑ¿ŠÖ‡" },
			new String[] { "Mo",
					"ÃþÄ¥Ä¨Ä©Ä¤Ä«Ã»ÄªÄ¬Ä§Ä£Ä¦Ä¡Ä®Ä°Ä¢ÂöÄ­ÍòÎÞÃ°Ä¯ï÷ñ¢éâïÒæÆÚÓÝëõöõø÷áÜÔâÉñòÃ´²®°Û„¯„¹Îð†ù‡±‡¶ˆ\‰sŠ‹‹ºŒ­Ž’ÅÁŽ”\‘½‘Û¸§“á”V”}ÃÁ•b•½–£˜íšzš{›]žfŸo jªC°Ù°t±u±‰±‹²a²h³]µc»Š¼U½Q½]¿}ÀgÅÇeÃêËÍˆó¡Ï_ÍàÑJÖƒÖ„×OØ{Ø€ºÑÃ²ã€æŸì…íHðxð‘ò‡órôŽôžüNüOüaºÙæÖ" },
			new String[] { "Mou", "Ä³Ä±Ä²íøòÖöÊÙ°çÑßè¼þ„ÀÛÌ…ÞˆéæÄc”––üÎã›£²y¿ŠÏwÙóÖ\ãwíJøœüEòú" },
			new String[] { "Mu", "Ä¾Ä¸Ä¶Ä»Ä¿Ä¹ÄÁÄ²Ä£ÄÂÄºÄµÄ´Ä¼Ä½ÄÀÄ·ÀÑîâë¤ÛéãåØïÜÙ„L‰ŠÃæÄ\Ž¿‘H–]—ú˜Òš»šÒžÑ ¸ ñª…®r®y®€®®Ž³c¿}¿ŠçÑÃkÅÆŸÇ€ÈrÍ]Û[ãaãfë‚ëŽíJß¼ºÙ" },
			new String[] { "Myeo", "”æ" },
			new String[] { "Myeon", "C" },
			new String[] { "Myeong", "—Ò" },
			new String[] { "N", "àÅ†Hßç" },
			new String[] { "Na", "ÄÇÄÃÄÄÄÉÄÆÄÈÄÅÄÏñÄÞàïÕëÇpƒÈÄÚ…ÈßÎ†òŠ{’f’‚’œV¶g¸—¸™óèºO¼{ÐõÉSÉiÐœÔFÔiÕyØvØyÛÜ˜àGâcæ“ë~ì„ô›" },
			new String[] { "Nai", "ÄËÄÍÄÌÄÎÄÊÄÄÝÁÜµèÍØ¾Ù¦‚™Š…‹èŒYi’í“ˆ“¯œ‡ŸÃ¯GÂYÄÜÄGÎ—Ñ”Þ•áår" },
			new String[] { "Nan", "ÄÑÄÏÄÐôöàïòïéªà«ëî‚OàîŠ{ŠÉ‹R‹©m‘Ú’o“DÌ¯”‚•¨–––¹œ¯Ì²ž©Ÿ²®~Ç~ÈlÖQßaëyò¢" },
			new String[] { "Nang", "ÄÒâÎêÙàìß­eƒ²ßæ‡‡°~‘“r“î™ò›ïžž²ÌZÐL×að–ôTýQ" },
			new String[] { "Nao", "ÄÖÄÔÄÕÄÓÄ×Ø«îóè§ÛñßÎòÍâ®íÐ…Dˆß‰ë‹C‹špŽHŽjŽuF˜À‘“Ï˜ï™`½½²«D«L´L´ZÃ—ÄQÄXÄžÎjÏuÔi×Dçtémô[" },
			new String[] { "Ne", "ÄØÄÄÄÇÄÅÚ«…È’fðÛ±„ÔGðÚ" },
			new String[] { "Nei", "ÄÚÄÄÄÙÄÇƒÈŠÌŠñšß›ÔÃ•ÄFåMðHð]õƒõ" },
			new String[] { "Nem", "Ÿˆ" },
			new String[] { "Nen", "ÄÛí¥‹\‹¯èÄÄQÄž" },
			new String[] { "Neng", "ÄÜ¸o¶øÄÍÎ—" },
			new String[] { "Neus", "Ç‚" },
			new String[] { "Ng", "àÅ" },
			new String[] { "Ngag", "â…" },
			new String[] { "Ngai", "äG" },
			new String[] { "Ngam", "†«" },
			new String[] { "Ni",
					"ÄãÄàÄâÄåÄæÄØÄçÄßÄáÄäÄÝÄÞîêêÇÛèìòâ¥Ù£âõöòíþì»ÃƒŒƒ“ƒ¹ƒºˆÐˆÓŠ…Šö‹¤‹è‹òŒNŒTŒÉŒÛáÚ›©îí«‘¹’f’v’í”M••¿–«—´™šîœNäÜðôž…ž— ù¯[±z¶[¶v»u¿QÂžÃfÄQÄÅMÆsËoÍeÍ‰ÎUÑAÓrÕy×rØƒÛCÝrÞ‹à\â‰ãbèXéSëWñDöFûŒýu" },
			new String[] { "Nian", "ÄêÄîÄíÄìÄéÄëÄèÕ³Ø¥ð¤éýöÓöóÛþ…`†P†ˆŠ¨’×“Ó”f˜^›ÝœVœÇ¯[¶j¶|ºvÅˆ³ÃÚfÚ™Û…ÛœÜTÝ‚ÝšÕ·õRöTùD" },
			new String[] { "Niao", "ÄñÄòôÁÜàëåæÕ‹–‹ØÞÍŒ³˜ÒÄçÆ›Ê\ÑUÑ™øB" },
			new String[] { "Nie", "ÄóÄøÄôÄõÄùÄ÷ÄöÚíÞÁà¿ô«õæò¨Ø¿Äß†Ç‡y‡§‡Ë‡Ù‡Üàïˆ[ÛþŒZT»ÔŽLŽqŽ‹ÐÒ¶Äí’í“I“”Éã“µ”z”¤–¨—´˜®™Ç¯[ºQ»H¼b¼fÂ™í±ÅYÆ}ÇŒÐAÒAÕ”×‘ÛWÛfÛhÜbãbãcäOäŽåRæ‡èXè‡èêEêŸïDým" },
			new String[] { "Nin", "Äúí¥‡á’ŒÃ€" },
			new String[] { "Ning", "Å¡ÄýÄþÄûÄüÅ¢Øúå¸ßÌñ÷‚Aƒ‘±ù‡“‹ÞŒ|Œ‚Œ„Œ‰ŒŽÄê”QÈÁ™F™ŽÃôªŸÒÉ²…ÂœÆrËfè_ôVôXûH" },
			new String[] { "Niu", "Å£Å¤Å¦Å¥ÞÖæ¤áðâîF’j–ƒ›S›\žÈ «¼~ÇyòÊâoì" },
			new String[] { "Nong", "ÅªÅ¨Å©Å§ßæÙ¯ƒzßÇ‡‘’˜’°™×â°J¶Z¶Œ·vÀYÄ“Ç_ÊÒaÞrÞsáxýPÞÃ" },
			new String[] { "Nou", "ññ†Ž‹ç“x”J˜‰™“«AÁ…×a×kæeç" },
			new String[] { "Nu", "Å­Å¬Å«æÛæÀæååó½ö¹Â‚ÕßÎàû“x”J³e¹@ÈìÔiñw" },
			new String[] { "Nuan", "Å¯Šfœqœ¨å¦ŸœŸð`" },
			new String[] { "Nue", "Å°Å±ÚÊ³–" },
			new String[] { "Nun", "üQ" },
			new String[] { "Nung", " \" },
			new String[] { "Nuo", "Å²ÅµÅ³Å´ÄÈßöÙÐï»Þùƒ®…ÈÄÅÄÄˆë‹s‹µÞ‘Âµô’ý“x“—j˜`™DšÃœx·L·z¼K¼XÂXÑDÑEÖZÛßSÄÇàGåŸÄÑëyÐè" },
			new String[] { "Nv", "Å®ô¬îÏí¤–H›\áð»sÐõÂxëÇÐZâS" },
			new String[] { "Nve", "‹FÅ±¯‘Å°" },
			new String[] { "O", "Å¶à¸àÞ¹p" },
			new String[] { "Oes", "‰ñ" },
			new String[] { "Ol", "j" },
			new String[] { "On", "•jíM" },
			new String[] { "Ou", "Å¼Å»Å·ÅºÅ¸ÇøÅ½Å¹âæê±Ú©ñî…^…¾…Ë‡I‰p‘Y¿ÙÎÕ“¸”·™¯šWšªä×aÏŸà®T¼uæúÄUÄpÉ’ÊqËšÖŽÓöáqæ–økútý{" },
			new String[] { "Pa", "ÅÂÅÀÅ¿Å¾°Ò°ÇÅÁÅÃÅÉóáèËÝâ°È°ÉŠrŽ°Ñ’öšñ°qÅu°ÅÆtÐ’Ú•âZîÙ" },
			new String[] { "Pai", "ÅÉÅÅÅÄÅÆÆÈÅÇÅÈßßÙ½Ýå·È—“—À ÛªT¹uº’Æ¢ÄMÝ‡æW" },
			new String[] { "Pak", "´s" },
			new String[] { "Pan", "ÅÌÅÎÅÐÅÊÅÏÅËÅÑÅÍ·¬°ãÅÖñáó´ñÈãúÞÕãÝõçZ°éƒë°ë±åˆmŠ™æ©‹ŠÉóŒqŒŽ´ÛÍÑå°â°è“„–®˜„›c›œ°œãžbžcžÎÆ¬ ž ¥®‰ð«±P±_±e±~´B´‘»O¿T·±Ä‡ÉgÎŒÑ—ÔjÛAÛsÛ˜Û¶äƒæoè‹é›íQîGùb" },
			new String[] { "Pang", "ÅÔÅÖÅÕÅÓÅÒ°ò°õäèáÝåÌó¦·Â°ø…€†ç‰â‹˜Œ´ÅíÏ·¿·½›P›`žÐºUÃTÃpÄtÅ}ÝòÐIÓIÚ“·êæ^°÷ë„ìQóoö„÷›ý‰ý‹" },
			new String[] { "Pao", "ÅÜÅ×ÅÚÅÝÅÙÅÛÅØáóÞËâÒðåëã°ü‡¥ˆƒŠE±§’“¿žä  Ü­”°’³hµPµ^·…·•°ûÃ‡°úÈaË‘ÍdÐˆÑŒÖcÝNãEè˜ìŽïRì©õU±«ûûƒüB" },
			new String[] { "Pei", "ÅãÅäÅâÅÞÅßÅåÅàÅæÅáì·ïÂàúõ¬ö¬àÎ¬‚_±¶·È»µˆ¡åúŠvŠ³Šç‹fïC’yÞå”h”ä•^–È—“šÅ›ÖäÄ é«˜¬a¬i¸ŸÃSåõÜØÆžÉ„òãÐ[ÑpÙrÞ\äžêkêŠñ]ñs" },
			new String[] { "Pen", "ÅçÅèäÔ·Ô…Ü†Ï‡Š±¾š\·Úå­›ÁÂMÈ†Ðv" },
			new String[] { "Peng", "ÅöÅõÅïÅéÅîÅóÅíÅôÅëÅðÅòÅêÅìÅñâñÜ¡ó²àØºà‚‡°ø‚õ„™„ú‰X‰k×¯‹y‘u’²’ü“s“žÅÔ—Z—Ä—Õ˜¨˜Õ›€œAœKäèmpŸÔ¯n°v³y´y·@¸†ºU½lÀeÃgÆMÆ»ÇLÇlÏeÛsÝJÝZÝ~Ýƒ±Å·êßJåAèméoíŠíŽñsòuó—óŸôJùi" },
			new String[] { "Peol", "›¹" },
			new String[] { "Phas", "Ž‡" },
			new String[] { "Phdeng", "êC" },
			new String[] { "Phoi", "n" },
			new String[] { "Phos", "†Ô" },
			new String[] {
					"Pi",
					"ÅúÆ¤ÅûÆ¥Åü±ÙÅ÷Æ¨Æ¢Æ§Æ£Æ¦ÅùÅýÅþÆ¡Æ©Åø·ñõùØ§ÛÜæÇñ±Øòß¨Û¯ê¶èÁî¢òçÜ±ÚüßÁÚéîëâÏî¼Ûýç¢ÚðäÄàèò·ñÔ¶ÉÙÂ¸±±°‡‡›‡º»µÅà‰ªŠv‹œBšïàú±ÓâØW‘šÆË’y·÷“F”è–C–Š—À˜[±Èš³š·œk‡Ýå¨žÌŸ ò øªW¯@ñâðí¯w²D´iµFµG¶u¶y·K¸“¹vó÷ºfº”»z¼„ÁTÁ`Á‘Â\ÃYÃ˜ÄMÄmÜÅÆkÜÖÝÉÞ¬°öÍnÍoÎ“ÏKÐK±»Õ|ØuØwÛ¶âWâbâtââ”ãYãã›äšåCæqîÐêVêoëRí@îHîˆÆÄñyó‹ô“õBõQ÷‰øaùdúûG" },
			new String[] { "Pian", "Æ¬ÆªÆ­Æ«±ã±âôæçÂêúæéëÝõäÚÒ‡æ‹xÆ½Ì—è˜Fªp­p¾œÄAÈqòùñÛÒÕ—Õ›ÙGÙXÛM±çÞqñ‰òNò]ò_ójôú@" },
			new String[] { "Piao", "Æ±Æ®Æ¯Æ°ÆÓóªÝ³æÎî©éèçÎàÑæôØâƒG„Ü®Ò‘G“¿”ô‡ Ü°Ž´‚ºg¿~ÂH±ìÊEËiÖ€áoêQî’ïgïhòŠóQóTôw÷Ô" },
			new String[] { "Pie", "Æ³Æ²ë­ÜÖØ¯‹±“Å•È‡·Î±ÎÒ”çv" },
			new String[] { "Pin", "Æ·Æ¶Æ¸Æ´ÆµæÉé¯æ°êòò­Ø°‡¹æ³‹åÞÕ–Wšý±Ã«n¬V²‹³WµI·|ËdÌOØšîlïAñPóD" },
			new String[] { "Ping", "Æ½Æ¾Æ¿ÆÀÆÁÆ¹Æ¼Æ»Æº·ëæ³öÒèÒÙ·‚‡„R…ç‰BŠÐŒÎJŽ—Ž£Ž±‘k‘{™q›€›¯›ÚœKŸv«r®J®j³fÅé³y¸z¹’ºqÀÂ†ÃgÆEÇLÉ‘ÌOÍgÍƒÔuÝZÝƒàZãuîZñTõGÚ¢³Ó" },
			new String[] { "Po", "ÆÆÆÂÆÄÆÅÆÃÆÈ²´ÆÇÆÓ·±ÆÉóÍð«îÇÚéÛ¶ê·ØÏçêîÞ†\‡MŠUŠËŒžŒ ŒÛŒûFŽˆg“„”’•^—K—á™›¨œ_œ”œÂäßŠžTŸBªt°~³kÁ‘²²ÉbÊXÖcõËáNáwá•ãOçk°ÔîHñFñpómãø" },
			new String[] { "Pou", "ÆÊÞåÙö…Ä…ð†Vˆ¡ÅàˆøŠç’g’h±§’½—”¸¢ Á¹rÑf°ýÒJõÛ²¿à^äžïÂïH" },
			new String[] { "Ppun", "ƒÍ†R" },
			new String[] { "Pu", "ÆËÆÌÆ×¸¬ÆÍÆÑÆÏÆÓÆÐÆÎÆÙÆÒÆÔÆÖ±¤ÆÕ±©ïèàÛÙéäßå§ë«õëè±ïäƒW„ƒ°þ²·‡þˆO‰Ž}Ž~·ö’p’Ã“ä“òê·•®–¿˜ã™kªžÊŸMªŽ¸¦¯j²r³h¶·o¹rÀbÅmÅnÜÞÇ[ÇŽÉhÍ—ÒLÒiÖE×VØfÙŸáTäçhç’ê†ñmõ‹ùLë¶ÆØ" },
			new String[] { "Q", "è£" },
			new String[] {
					"Qi",
					"ÆðÆäÆßÆøÆÚÆëÆ÷ÆÞÆïÆûÆåÆæÆÛÆáÆôÆÝÆâÆñÆöÆúÆüÆîÆàÆóÆòÆõÆçÆíÆÜÆèÆêÆé»üÆù¼©ÆãÆýÆìì÷ñýæëá¨áªõèÝ½Þ­èçí¬ÜÎÜùÝÂÜ»ãàØ½÷¢Ù¹éÊàÒòÓôëØÁì¥ç÷÷èçùòàÛßè½ÝÝíÓä¿ìóêÈç²Ø¢…¼¿‚ˆÙÊ‚úƒ[ÇÐ´Ì„~ÇÚ³ÔÖ¨…Ñ…æ…ý†u†ƒ†™†š†¢†Ð‡rˆÎ‰óŠÝŠíËÞŒóÃ¼ºŽ©æâåôâéjí¢¢Ôæ÷‘h‘i‘s‘¼’M’Q’W¼¼µÖ’†êü’Ý’å½Ò“ Ö§”Œ”ª”Å”Æ”ç•’•´–OÖ¦–Ö—R—t—‰—Ž—¤—«˜™‡™–™ûš©šÝšâ››¼ÃœDœg×ÕœjœŒœœënùúžÅŸdªX«O«^­D®P±Â»û¯O°ž±[³H³ž´J´\´m´w´ƒ´„µJÊ¾µo¶Q¶S¶¸gº‘º“»K»ž¼–½e¾L¾Nôì¾_¾e¾z¾ƒÀdÀŽÀ™ÃIÃXÄšÅpÅ ÆZÈWËjËsÌIÍTÍVÍ[ÎBÎ‰ÏBÏlÏ„Ï“Ð}ÑEÑvÑwÑzÓsÓ™ÕƒÖHÖ[ØMÚ|õÁÚ–ÛaÛeÛpÜeÜjÜ•Ü™Þ€¶ºßŒàVàœâHåWçKçˆèŸêMëBë’í ð‡òTòUò€ôGônôoôtôyõlõšö’÷’ùuù}ù†û˜üýRýt" },
			new String[] { "Qia", "Ç¡¿¨ÆþÇ¢÷ÄñÊÝÖÒƒrƒîßÒˆX¿ÍŽ˜Ù’u’‰êü“U“Š“ü˜Hšðâ³L³s´l½eÚžáMì—õ^öÚ" },
			new String[] {
					"Qian",
					"Ç°Ç®Ç§Ç£Ç³Ç©Ç·Ç¦Ç¶Ç¥Ç¨Ç¯Ç¬Ç´Ç«Ç±Ç¸ÏËÇ¤Ç²Ç­ÇµÇªá©îÔå½óéÞçåºÙ»ã»ã¥í©ò¯ÜÍÝ¡ç×ÙÝÜ·ÚäëÉÜçèýêùå¹q¤½‚]‚¡‚ßƒLƒŽÆàÛÉ„X…•†k†éˆTˆU‰q‰‰‰µŠdŠú‹`‹ì‹üŒRÕ¯ŒòÒQâãøŒ‘a’R’ƒ’Š’®’ç“B“b“¾“Ã“ËÞþ”o”p”q•ü–e–}¸Ì—˜p˜˜ ™N™Œ™¥™÷šKšMškšþ›Fä¹œDœ\½¥äÕu“žKžUžž£žèŸšŸÈŸï R ¿°|¸d½î¹ˆºGºRºž»R»`»x¾P¿yÀ`ôÇÁuÁ{ÃëìÄdÅOÅˆÆgÇ@ÇMÈ“ÈœÉ`ÊgÊnËÍOÍZÎSÕÖt×lØ@¸ÏÜÝ€ßwâTâ`âjã@ãQãUäEäuåDåXæPæZçcèBècè~ï·é_ëeìyíaîvñUò`òcòqôRôSöö‘÷œøZùkúYûeðÏübýlö¸" },
			new String[] { "Qiang",
					"Ç¿Ç¹Ç½ÇÀÇ»ÇºÇ¼Ç¾½«òÞõÄê¨ñßãÞìÁïêïºïÏôÇéÉæÍ„“„ßßÑ†…†“†Ü†ó‰‚‰¦‹ÔŒ¢èÇìZŠ™‘c‘ê¿Ø“Œ“¬“°”Ö—¾˜Œ™{š£œÙ\ŸÍãÝ › ª]ª}«o¬j¬š³Móäº[¿‹ÀHÁmÁuÁzÁ†ÅšÊ@ËNÌbÓHÖmõ¼Û„Û–äæjçIçjî›úIû]" },
			new String[] {
					"Qiao",
					"ÇÅÇÆÇÃÇÉÇÌÇÂ¿ÇÇÊÇËÇÄÇÎÇÏÈ¸ÇÇÇÈÇÍÇÁéÔÜñõÎíÍã¾ÚÛ÷³ã¸çØÚ½Øä‚¸ƒSƒsÏ÷„ä†Ì†×‡a‡„‰U‰Œ‰”‰§‹´á½ÏþŽŽ»ŽÉ³îÕÐÉÓ¸ã“³“êë¸Ð£˜“˜ò™]š¤š¨ë¥‰½¹Ÿ}ŸòŸ÷ Ö¯ ±³~Ïõ´`´x´“´™´¸G¸[¹›¿”ÀRÂNÜúÇJÇŸ½¶ÊwË–ÏfÕV×K×SÚˆÚ‰õÓÛXÛ^ÜEÜFÜNàbàzàƒà…õ´á ã“å æ@çDçyèAï¢ê~íIíXímîNî–î˜òœ½¾ófó|ó~" },
			new String[] { "Qie", "ÇÐÇÒÇÓÇÔÇÑÆöÛ§ôòã«ïÆæªóæã»Ù¤êü‚Œ‚ž‚Í…L…‚ßþà©Æõæ¼‹}Ž¨‰Ü½Ý–A—¸Æã›­›ùÆá¯C°m·G·l¸`¸›ºD»]¾fÂëâÆjÞªË~Í„Í‰ÔˆÛBÛoå›çƒôŠö@ölöø" },
			new String[] { "Qin",
					"Ç×ÇÙÇÖÇÚÇÜÇÞÇØÇÛÇßÇÝÇÕßÄñûñæôÀÜËäÚâÛàºòûàßÞìéÕï·ƒ¡……Â†wˆaˆ¨ˆ²ÝÀ‰ƒ‹]‹ŽŒ€Œ‹Œ˜ÂôûŽÜQø‘[‘¥‘¦’R’a’Í“l“å”Ü•T—véÈ™N™ÂšJ½þ›ØÉøBàäžp«¬l¯²›¸½ÂlÃQÅOÇ™Ç›ÈBÝèÞ­ÌCÌIÍZÏOÏˆñÆÓHÕWÚ_Úcâ\âdâsäuëdì€îMîzî›ò¢ñŸóVôgõøV" },
			new String[] { "Qing", "ÇëÇáÇåÇàÇéÇçÇâÇãÇìÇæÇêÇ×ÇäÇèàõö¥éÑóäÜÜòß÷ôóÀöëíàôìÙ»ƒAƒ ƒõ„…„Í†¦ˆ½Éù‰ð‹]ŒxŽöF‘c’á“÷•¦—³˜½™”™¼š„š šä›Üœ[œ‚NžD«l®_³|³³ ¾«¾PÊ¤ÇmÈÕˆÝXÝpàWè[ìiìmí•õ›ù‚" },
			new String[] { "Qiong", "ÇîÇíõ¼ñ·ÚöòËÜäöÆóÌƒ’…o‹ÖŒ^Ä‘w–÷™KŸwŸzŸ¦ŸÅ¬I­W­‚­Ž±ž²`¸F¸\¹HÅ|Ë}Ë•Í‹Ú^¾Ï" },
			new String[] { "Qiu",
					"ÇóÇòÇïÇðÇö³ðÇñÇôÇõ¹êé±òÇôÃôÜòøÛÏåÏÙ´ò°êäöúáìäÐ÷üåÙHÇø…œ…´†pÍÅˆw‹pËÞŒx¦Ž€nã°ã¸’@’º“z–_—W™Ïš‚šÂÙÛšðšü›½œrœ©œªŸª ³«U­G°“±H¶k·hºE¼z½‡¾ÃFÜ´ÇiÈcÉ’ÌUÍAÍÎ~ÏbÐ@ÓaÓpÓˆÓ‰ÙgÚzÚ‚ÞÚþábáá–âUäMîÅíFíGØ¸õFõ‰öpöq÷A÷GøFùjù”ð¯ûjý”ý•" },
			new String[] {
					"Qu",
					"È¥È¡ÇøÈ¢ÇþÇúÇ÷È¤ÇüÇýÇùÇûÈ£Ðçó½Þ¾ìîÞ¡íáÛ¾Ú°ð¶ãÖôðñ³áé÷ñè³ë¬êïòÐëÔöÄá«ÜÄÇÒÚ„`…J…Z…^…¾ä…íˆoŒþEç¾ÞlßI‘t‘ó’|”·”×™á™úšª›µœTž›Ÿa­S¸l¸y¹L»c»–¼ ½M½P×éÁ”ÂJÂ^ÃaÃlÃÅJÇ†ÈÍmÀ¯ÎƒÏJÏgÐRÐdÐ ÓNÓUÓYÔsÔxÕFÕoÚmÚzÚ…Ú ÛBÛRÜdÜ|Ý@Þ‘åáàTã^èLèŠé‰é˜êr¾Ï÷¶ñlñnò|òŒæãó”õ@õLö÷OøzùŠûYüCüDüLüzüšýxØÎ" },
			new String[] { "Quan",
					"È«È¨È°È¦È­È®ÈªÈ¯È§È¬È©îýóÜç¹Ú¹éúî°÷ÜãªòéÜõç„á„ñ¾í†­‡üÛÚˆ»ŠºŠ÷ŒAZŽkŽ†ƒw³Ë©’Ô“‘–Õèð—¨—Ñ˜T˜¤˜Ø™à›L›§œ²žµžï º » Åâµ¬g¬†®l²•³o¼ƒ½h¾J¿X´¿ÄCÈ›Ì†ÐSÓjÔÖw×NÛIÛmÝbà ãŒçzêBíjïEñògöe÷™ûXðÙýjáë" },
			new String[] { "Que", "È´È±È·È¸È³ÈµÈ²È¶ãÚã×í¨‚à¾ö…sˆ«‰U‰”Çü‚â‘U“nß«”¦š£š¨šõ›Q PÁÔª“°”³‚´F´_´`µCµ]ÅbôªÉÖÉUÍXÚ|ÛeÜeé êIëaøBùo" },
			new String[] { "Qun", "ÈºÈ¹÷ååÒ‡ï‰æŒlnŽ šV¹„ÁtÑdÛZÝl¶ÝûŠûŽ" },
			new String[] { "Ra", "’Á@" },
			new String[] { "Ram", "‡Ý" },
			new String[] { "Ran", "È¾È¼È»È½÷×ÜÛòÅƒÑ…m…ß‡YŠ˜‹v–¹™LŸß«z¿‘ÃVÅjÉGÍcÐ€Ð…Ð™Ûœó†" },
			new String[] { "Rang", "ÈÃÈÂÈ¿ÈÁÈÀð¦ìüƒ¨„ð‰´‹úÝ‘Ó™Öž }«K·yÀvÌZÐL×j×ŒÜ`è‚ÏâôX" },
			new String[] { "Rao", "ÈÄÈÆÈÅÜéèãæ¬‹ÆÄÓ“Ï”_˜ï á·n¿À@çÔÊÏuÒYßvëNðˆ" },
			new String[] { "Re", "ÈÈÈôÈÇßöÙ¼’ÚœcŸáÛ" },
			new String[] { "Ren", "ÈËÈÎÈÌÈÏÈÐÈÊÈÍÈÑÈÒÈÉâ¿éíØðÜóÝØñÅïþ¡¶ù„UŠžŒãáäí¥’P–Z–k–ß–á—e—ª›Ý ®¶e¶‰¼x¼Œ½V¾BÀÃMÄHÆ\ÇYÇŒÑGÓ•ÕJ×šÜrÜâJâmã…ìzì~ígïƒïšôøžØé" },
			new String[] { "Ri", "ÈÕ‡ðšÞâJâ~ñ_óR" },
			new String[] { "Rong", "ÈÝÈÞÈÚÈÜÈÛÈÙÈÖÈØÈßÈ×éÅáõáÉëÀòî‚Ô‚æˆc‹†‹’‹æŒ]tÊŽVŽc“m“r“–•í–Ñ˜s˜xš¿šÕžqŸV h¬Œ·Z·\½q¿^¿dçÈÁsÆŽÎÏ”Ñ’ÝPægéF¸ôížËÌñŒó“" },
			new String[] { "Rou", "ÈâÈàÈáôÛõå÷·…œ‹YŒ`˜QœnŸ§¬y­~¶b»€Ä\ÇyÈ|ÎjÝŠåˆè`íqòkóökù’" },
			new String[] { "Ru", "ÈçÈëÈêÈåÈãÈéÈìÈèÈäÈæÝêñàï¨àéçÈå¦Þ¸ò¬äáä²û‚¢…Ê†B†äÅ®‹‡‹çŽ]Žš’C’”J•ãÔÂ–d–ô™“œx ^«A¹T¿dÀ]ÈâÃNÄžÉSÊ‡ÑMÞzßàrá}ãœè`Ðèîž÷pøMønø›" },
			new String[] { "Rua", "’µ" },
			new String[] { "Ruan", "ÈíÈîëÃ‚¢ˆë‰¼‹\‹¯Þ“É™“œxå¦ ^¬}­w´MµO¾ÂXÄQÎpÜ›Ý‰Ðè" },
			new String[] { "Rui", "ÈðÈïÈñî£ÜÇò¸èÄÞ¨ƒµƒ¶¶ÒƒÈÄÚ…±‰ÇŠñ»’f“É—M—‡™G›I®c¸½—¾qÀBËçÆÊtÌGÌHÎTÛbâcäJä„çiÄÆ" },
			new String[] { "Run", "ÈóÈò“É˜ô™écét" },
			new String[] { "Ruo", "ÈôÈõóèÙ¼…ª‹S×ÈÇ’µ’Ú“É—íœcÄçŸx kºOÜÇÉmàeö}ö”úU" },
			new String[] { "Sa", "ÈöÈ÷ÈøêýØíØ¦ìªëÛ“—”c—Eéß™¨š¢›‡¥ž¢À{²ÌÊ”Ë_ÔQÜaâlæpçoè•ëMëìƒì‘ïSñ`" },
			new String[] { "Saeng", "–Ó" },
			new String[] { "Sai", "ÈûÈùÈúË¼Èüàçƒw†ð‡Tà“HšºšËºwº›Ùî|öw" },
			new String[] { "Sal", "oÌƒ" },
			new String[] { "San", "ÈýÉ¢É¡ÈþâÌôÖë§ö±‚^‚ã‚ð…x²Î…¢…£…¤‰ÐŽ¥q™VšÉšÐ¥ Ñ¼B¼R¼V¼W¿™ÊQÖçDçoédð€ôLáêãß" },
			new String[] { "Sang", "É£É¥É¤òªíßÞú†Ê–ø˜šÀvÑ˜ærî‹" },
			new String[] { "Sao", "É¨É©É¦É§ÉÒÜ£öþëýçÒðþÉÚý‘¨’ß’û™]š×œÐŸ¯Ôï²„¿„¿‰ÀRçØà“èAïbòXò}óöYö…öŸ÷f÷“" },
			new String[] { "Se", "É«É¬ÉªÈûØÄï¤ð£†ÝÕ¯Zå‘­’‘“ö–ÜéÊšmšoÆü››œi×Õœàn­®æíži¬X­i¯™·w·†»ÀNÇ¾ËNÌŸÖ ÞQãGäCæaæ|çmîéï¡êSëïo" },
			new String[] { "Sed", "ÑS" },
			new String[] { "Sei", "›ØÂ{" },
			new String[] { "Sen", "É­²ô“½˜¦ÉøBºdÒI" },
			new String[] { "Seng", "É®ôO" },
			new String[] { "Seo", "é~" },
			new String[] { "Seon", "¿L" },
			new String[] { "Sha", "É±É³É¶É´ÉµÉ°É²É¯ÏÃÉ·É¼àÄßþöèö®ï¡ððôÄêýì¦o‚ƒƒ„x†~†—†ÃÒ­Ž¨B’­½Ó“”Éã“—”z˜f˜×š¢çªQ³¹€»}¼†¿À\ÁœÁ ÇÈSÊeÙdÙhÉÞæ|é„éŒëô‹õõ" },
			new String[] { "Shai", "É¹É¸É«õ§“—”ƒ•ñš¢ºYºkº»iÀ\ÐgÖLéŒ" },
			new String[] {
					"Shan",
					"É½ÉÁÉÀÉÆÉÈÉ¼É¾É¿µ¥Éº²ôÉÄÕ¤É»µ§ÉÅÉÂÉÇÉÃÉÉæÓóµÜÏìøõÇÛ·äú÷­æ©ØßæóðÞëþÚ¨îÌô®Ûï‚ÞƒRƒdƒ{ÙÙ„h„š…g†Î‡AˆZ‰‰Ž‰¯Š™ŽEŽ»’´’ï²ó““½“ú”v”»••Ú•Ü–u–Å—Ö˜èÌ´™c™Ò ¿å£ž¨žèŸSŸšŸÄªGªk¯Z±˜´Š¶U·_¸–¸ž¿„¿˜ÀuÁÁƒÃˆÈÊ`ÏsÏ€ÒIÒvÓ@Ó˜Öb×iÙ Ú]Ü‘µËßáŸãˆç—éWéXé^ê„îtî²üðƒò~õŠ÷W÷X÷gø@áêÛÉ÷Ô" },
			new String[] { "Shang", "ÉÏÉËÉÐÉÌÉÍÉÎÉÊÌÀÉÑìØõüç´éäÛðA ‚û³¡ˆÃˆö‰jŒ¬vÕ‘^‘ûš‘œ«Cg¶@¾yÊKÏDÐLÓxÖ…ÙpÛ}èlì ôl" },
			new String[] { "Shao", "ÉÙÉÕÉÓÉÚÉ×ÉÒÉÔÉÛÉØÉÜÉÖÕÙÇÊÜæÛ¿äûô¹òÙóâÔÏ÷…pŠ¾„ÕÐËÑ”ï–¶äÑŸ†Ÿý d«x±óÔ½B½‹¾Kç¯ÇzÈVÈpÊ–ÐŒÝiíIímïYó™õ}è¼" },
			new String[] { "She", "ÉçÉäÉßÉèÉàÉãÉáÕÛÉæÉÞÉâÉåÉÝì¨ØÇî´â¦÷êäÜÙÜ…‡ŠL‘b‘Ø’wÊ°’¡’ÎÞé“”“º”z™™Ý›õœhž—®Œ½Þµú´’ÄôÂ™ÅhÈ~ÊJÍFòÒÍ…Ï‡ÔOÙdÙhÝfêAê^íHísòM" },
			new String[] { "Shen",
					"ÉíÉìÉîÉôÉñÉõÉøÉöÉóÉêÉòÉðÉë²ÎÉéÊ²ÉïÉ÷ÝØôÖÝ·Ú·ÚÅïòé©äÉò×ßÓëÏ·ê‚LÐÅƒÂ…¢…£…¤ßÅ‡AˆÞŠŠ·‹Ž‹ðŒJŒqŒŒævŽ»zõ’J’bÞÓ“•Y•Ö–¸—ª—Ø˜Y˜¦šá›ØœVBžcŸö«|®`®e¯}¯”±m±s²_²s² µŠ·Œ»p»r¼¾DÁAÁKÃŒÄIÈÉ†ÊQËMÍ–Ñ[Ó\ÔBÔYÔ–Õ”×}×Ÿß•ãhävÕðîTñ‘ôõŠõ˜öYö•öŸ÷“ù_ül" },
			new String[] { "Sheng", "ÉùÊ¡Ê£ÉúÉýÉþÊ¤Ê¢Ê¥ÉûÉü³ËêÉäÅíòóÏáÓØ©\‚¯„„Ù…ÖÛÑ‰˜ÐÕŠ¿ëô‘™”Î•N•…•ú–™˜|™Tš}š ›ˆœƒœ¤ÆŸ„ õ«{¬]µé¸i¹“¿IÀKÂ}Â•ÆÊo×WÙKÙ‹ãHå•êjê…ê’÷jù|ü›" },
			new String[] {
					"Shi",
					"ÊÇÊ¹Ê®Ê±ÊÂÊÒÊÐÊ¯Ê¦ÊÔÊ·Ê½Ê¶Ê­Ê¸Ê°ÊºÊ»Ê¼ËÆÐêÊ¾Ê¿ÊÀÊÁ³×ÊÃÊÄÊÅÊÆÊ²Ö³ÖÅÊÈÊÉÊ§ÊÊÊËÊÌÊÍÊÎÊÏÊ¨Ê³ÊÑÊ´ÊÓÊµÊ©ÊªÊ«Ê¬õ¹ÝªÛõîæóÂöåöõêÛéøÝéóßìÂÚÖß±õ§ó§Fd~ËÛƒ½ƒà„Ý…b…„…«…Ú…á†Fßò†‡uˆËµÌ‰PŠ]Š¸‹q‹ÒŒgŒjŒpŒŒËÂŒÆ]«ÖŽŸsåèÊçô^É‘÷Ìá“JË¹•E•g•r–§–É–ò˜N˜V˜t™yÖ­›n››¸Òºœ›œ¢œÒœÛœáÉÌñžøŸ³ªHªLª{¬‹®‡¯a±c±i±x±ìêµu¶_¶ƒ¸b¹E¹GÉ¸¹•ºIºYºº »i½JÀ[ÒïêÈÖ«ÃeÉáÅkÈžÉNÉPÎgÎtÑ|Ñ ÒnÒ|Ò•ÓlÓ”Ô‡ÔŠÕœÕžÖu×RÙBºÕÛJÝYÞyßYßfßmßrß}ßŸºÂáyá‡á‹áŒâPâ‹ââžãAãBãJãväKå~åœæ|îèï¡ïzï†ï—ðOðSâ»âÁñ\ñ‚ô˜õZöXö|öˆö‰øOø[úPû\üœüýaýk" },
			new String[] { "Shou", "ÊÖÊÜÊÕÊ×ÊØÊÝÊÚÊÞÊÛÊìÊÙô¼á÷ç·…§‡bˆ–‰Û‰ÞÞÐ’ö”™ÌÎ›ìýª•«F¯l¾RÄfá~æ" },
			new String[] {
					"Shu",
					"ÊéÊ÷ÊýÊìÊäÊáÊåÊôÊøÊõÊöÊñÊòÊóÊçÊêÊëÊßÊèÊùÊúÊûÊüÊíÊþË¡ÊàÊîÊâÊãÊïÊðÊææ­Þóïøç£ãðë¨ëòÛÓÝÄì¯äøÙ¿‚J‚TÓá‚m‚‚ƒ©ƒÊ†CÊÛËÔÈ¢ŠìŒFŒ¥Œ«ŒÙŽõóXƒ’¼’¿’æÞí”d”µ•¤•ø–XÖì–€èÌ–µ˜Ð˜ä™]šÌšÑä³ˆ©òž‚ŸY¬Ÿ­qñâ¯E°P¶•¸w»P¼^¼‚¼Ÿ½R½ˆÁ›ÇOÝ±ÉDÉ[Ë\Þ´Ë’ËŸÌ ÐOÐWÐgÑVÒeÒlÖ‘ØQÔ¥ÚHÛSÛ\Ü“Ý”Í¸àgÒ°ã_åfçTèCïíêx³ýêœõ_÷n÷tùeùŽú–úžðÖü“âàØ­" },
			new String[] { "Shua", "Ë¢Ë£à§ËôäÌÕXÑ¡ßx" },
			new String[] { "Shuai", "Ë¤Ë¦ÂÊË§Ë¥ó°…iŽ›½—¿\ËçÀŠ" },
			new String[] { "Shuan", "Ë¨Ë©ãÅäÌŒ£–Õ˜¤ÉÇÄYõßéV" },
			new String[] { "Shuang", "Ë«ËªË¬ãñæ×‚ö‰u‹þ‘S˜¾™ÜäÈœöwž{ž“µd¿YÆCç`ëpò‚óLóZú{ûUût" },
			new String[] { "Shui", "Ë®Ë­Ë¯Ë°ËµŠÜŽœ’¨’Éšì›ä›çµˆ¶ÃŸÑcÕfÕhÕléjãß" },
			new String[] { "Shun", "Ë³Ë±Ë²Ë´¿¡çÝÑ²eâþ˜J˜ù±†²i²pÊŠÝí˜ôB" },
			new String[] { "Shuo", "ËµÊýË¶Ë¸Ë·Þ÷åùéÃÝôîå†dàÊËÔšFšõ›«ËÝåª d qª“¯Ÿ²´T¹›Ò©ÈpËŽÕfÕhãˆælèp" },
			new String[] { "Shw", "ÕÛ" },
			new String[] {
					"Si",
					"ËÄËÀË¿ËºËÆË½Ë»Ë¼ËÂË¾Ë¹Ê³ËÅ²ÞËÁËÇËÃËÈñêæáÙîòÏØËãáïÈãôóÓßÐð¸æ¦ÛÌçÁìëäùÙ¹ÒÔËÌý‚h‚Æ‚Ðƒ„@…‹Ì¨‡zŠÙ‹wŒKPáãlà–yÎö–Ÿ–Æ—t—ö˜{›q›…›—›åž[Ÿù µ´fìô¶D¶L¶T¸rºôé¼i½z¾ŒÀŸÁQÃBÒÞÝ¾Ê‘ÊœÌŒÎEÎ‡Î’ÏaÏzÒ–ÖpØ|Þ âLâ‘â–ãjãƒäFälæJçrïôï\ïtï~ïï•âÂñ†òIòlúfúƒûýDìá" },
			new String[] { "So", "ÏA" },
			new String[] { "Sol", "r" },
			new String[] { "Song", "ËÍËÉËÊËÎËÌËÐËËËÏñµÝ¿äÁã¤áÔÚ¡áÂâì‚‘‚ö…ºŠ»ØŽôè‘Z‘m‘¡’¿’Ö“K“¡–…–œ–·—s—Œ˜B™€Yë³—Â–ÉÌtÎ@ÔAÕbæJæïÈížðmñžó " },
			new String[] { "Sou", "ËÒËÑËÓËÔàÕà²ì¬ÛÅÞ´ïËâÈî¤äÑòô‚Ïƒð…®ŽùC’¿’È“¡“ß”\ë·×å—¯™¸šFä³’ªv¯˜»PÉLÉrË’Öjànágæ}æï`ïbðtòp" },
			new String[] { "Su", "ËØËÙËßËÜËÞË×ËÕËàËÚËÖËõËÝËÛãºóùö¢öÕÙíà¼ÚÕÝøä³‚ÑƒD…rà²‡ÕˆTˆUˆ¼‰O‹•å‘ˆ’Û°á“º—V—­˜j˜Â˜É˜þ™Åšƒ›ƒ›«œß’š«T«Ž­X®d´c·B·D·d¸@»¿i¿sÃCÄhÇxË‚ÌKÌVÔVÖqÚxÛ‘ßißpä_ðMò“óXõ‡÷Tú‰ûh" },
			new String[] { "Suan", "ËáËãËââ¡…W×«¯iµ{¸Œ¹gºeÑ¡ßx" },
			new String[] { "Sui", "ËêËæËéËäËëËìÄòËåËèËçËíËîíõÚÇå¡åäìÝÝ´î¡‚‚‹†a†÷ˆ¼‰åŠÌ‹ÓÀ’µ”ø™pšqšršË›ÔœñÜžvŸ«ŸÕ­j­…²B³Z¶X·[·uºw»‚´â½—¿\¿…¿“ÀZÀŠÃœÄŽÆVÇ]É¯ÈšËòÒ`Õr×\ÙwßUÒÅßzçiçwç›êyê ëSëmì[ìší}ól" },
			new String[] { "Sun", "ËïËðËñé¾Ý¥â¸áøöÀ†ÐŒOŽ…’X“p“q“˜Ê÷˜ƒ–ªs®p¹S¹º‹ÉpÊ˜ËVõÐæ{ïŠ²ÍúZ" },
			new String[] { "Suo", "ËùËõËøËöË÷ËóËòÉ¯ËôêýíüàÊßïèøàÂæ¶ôÈÐ©‚é†î‹‘ËêÀ»³­’­“™•­šqÉ³œÅœàÎþ Þ«I¬R¬­Fºwºz¿W¿sÇjÈšÎRË¥Ñ–ÚtåÒßCæ\æaæiææ•»ôì[óšô‹õ€" },
			new String[] { "Ta", "ËûËýËüÌ¤ËþËúÍØÌ¡Ì¢Ì£äâõÁ÷£í³é½äðåÝîèãËàª‚@‚è…ì…ú‡–‡Å‰‡Ì«„“‚´î“Ò“é˜dšÏ›øÊªœÍßêñ ­ªH«Hµk¶NÇEÑÕw×nÜDÜc´ïÞ…Þ‡ßQß_ßeãBåJæ]ædêFêSêYê`ì‘÷²ìŸíOí^õ]ö" },
			new String[] { "Tae", "ˆ‚Òk" },
			new String[] { "Tai", "Ì«Ì§Ì¨Ì¬Ì¥Ì¦Ì©ÌªÌ­ìÆëÄõÌß¾öØîÑÞ·Û¢ææƒˆƒè…õ‡òˆr´ó‰ûŠU‹êŒLçö‘B”E”Á•@–Ÿ™…œÌkžå M«}¹x»F»†ÄÜÅ_ÅvÇ ÔrÚ±Ü–áââ‘ïUñ~õT" },
			new String[] { "Tan",
					"Ì¸Ì¾Ì½Ì²µ¯Ì¼Ì¯Ì¶Ì°Ì³ÌµÌºÌ¹Ì¿Ì±Ì·Ì®Ì´Ì»îãÛ°ïâïÄñûå£ê¼ìþµ«‚„‚èƒNƒ{†®†ú‡@‡c‡dˆÅ‰›‰ ‰¯‰ÂŠòŽ—Z´‘…‘˜‘Ÿµ§“Ú“Û”Z”‚•Æ•Ò˜W™AšUÉòµ­Õ¿œžh ž©Ñ×­f¯a°D°c¶U¾gÀWÀ—Àµ¨ÅjÌòÅlÝ¡ÈIÊnÞ¦Ë“ÑgÒfÕ„×T×ZØØÙyêæá]áaávãgåUîtðZú‚ül" },
			new String[] { "Tang",
					"ÌÉÌËÌÃÌÇÌÀÌÁÌÌÌÈÌÊÌÆÌÂÌÄÌÅó«éÌôÊõ±è©ïÛÙÎâ¼äçñíàûï¦ó¥¹‚«‚Úƒ¯„¨†°‡RˆnÉµ¯Õ‘ÜÀ©“­”U”†•ò˜y˜ü™éœ«fgŸ¶ C ‡²˜´g¶KºLº‚¼C¼QÄgµ´ÉyÊŽËTÌoÎvÚZÛ}ÛßTàoæhæ†çMç|èKè’îõéEé‹êOêWãÑëGíUðhðnðyúSühü‘" },
			new String[] { "Tao", "Ì×ÌÍÌÓÌÒÌÖÌÔÌÎÌÏÌÕÌÐÌÑØ»ä¬ìâßû÷Òèºß¶—„ü†G‰ú‰üÒ¦‹—Žµ|þ’qÌô“†—ƒ˜…™„›ìý c¬•µŽ³ï»I½d¾I¾T¿_¿l³ñÀ‡ÎIÑiÓ‘Ô|ÖzÌøÝÞá[ä•åcìŠì’íNíwî\ï‘ðuñŠòP" },
			new String[] { "Teng", "ÌÛÌÚÌÙÌÜëøƒ\ƒ£Ž¸b¯\»L»T¿gÄ†Ì„ÎŸÖ`ß‚ìLñòvóIöŒü’" },
			new String[] {
					"Ti",
					"ÌáÌæÌåÌâÌßÌãÌêÌÞÌÝÌàÌäÌéÌçÌèÌëõ®ðÃç°ç¾ÙÃñÓåÑÜèã©‚m‚¨…††—†Ù‡¢ŠDŠ¸‹X‹qŒÏ¨ÃwµÜÊÓŠµÉ‘øÕÛ’«’ó“W”`ÊÇ–õé¦˜NšYš›¢œvzµÒ«Ÿ¬v­ƒíû´Y´f¶_¶”·a»G½¾ŸÁHËÁÆlÊƒËSÌŒÎyÏsÐ}Ñ{Ñ|ÔgÖBÖpÚ®ÚÐÚ„ÚŒÔ¾ÛyÛ‡ÜSÜnÜƒ´ïÞ…Þ‡ßPßXßmäRåaåç‘Îýî}òfóeówóƒóó›óžõkõ{ö[÷–ø˜ùYù•ù—úeúfú‚ÞÐ" },
			new String[] { "Tian",
					"ÌìÌïÌíÌîÌðÌòÌñÌóµèÞÝîäãÙãÃéåî±‚ƒÌµäÍÌ…×†ŠàÁ‰\ŠÇŠõŒ…ŒÄ¤’×“•‹èé›pÕ´œLœµá¬_¬™­k®\µéî®®s®x®ƒ¯t±]±™²V²_´[´k¸K½G¾gÃbÅjÅqÉ»ÈJ²ÏòÅÓCÓ`ÙqáLâšã”å`åŸææ‚èï»ÕòêDìjìpìtîŒîµßï›ø‰úcúlüV" },
			new String[] { "Tiao", "ÌõÌøÌôµ÷ÌöÌ÷ö¶óÔìöòè÷ØÙ¬ñ»öæÜæôÐƒ©†GßúÒ¦‹àŒiŒýGŽçf”Ó”þ•q–I–]ÌÒ—l˜Ôµx³í¸I¼g½rÂwÃxÃ‘Å—ÆKÈVÉ‚É‰ÉŠÏCÒ›ÕAÕ{³¬ÚqÚ}õÖã“äpæxï¢ì›î\õöœýf" },
			new String[] { "Tie", "ÌúÌùÌûÝÆ÷ÑƒcÕ¼…ãGÂÂzÍuµûÙNÛ@âŸã@ãŽä~ç“èFï°ï”òø‡" },
			new String[] { "Ting", "ÌýÍ£Í¦ÌüÍ¤Í§Í¥Í¢ÌþÍ¡î®ÜðîúÝãæÃòÑèèöª‚D‚K…ˆˆNµìŠcŠÇµŽØŽßadâ—H—þ˜w›àœsìŸNŸP«ž¬E¹j½–Â[ÂŠÂ—ÂŸÂ Ã‰ÆJÎbÕPÖFß‹äbéƒì˜îcïFüž" },
			new String[] { "Tol", "h" },
			new String[] { "Ton", "ª–" },
			new String[] { "Tong", "Í¬Í¨Í´Í­Í°Í²Í±Í³Í¯Í®Í©Í«¶²¶±ÍªäüÜíÙÚíÅá¼âúÙ¡àÌÛí‚£Ù×„¨„ç†L‡ìMdŽäÓÁ‘Q‘q•z•Ó–S˜¿™HšÔ¶´›Ïœ§žçžúŸ×Ÿü ‚ ÕªIª‘¯]±íÏ³‹¶‚·r¹c»½p½y½ŠÄ€ô¾ÉŒÍUÎVÏxÐhÔ˜ÚUÖØãPãnã~ï õj÷‹Øç" },
			new String[] { "Tou", "Í·ÍµÍ¸Í¶î×÷»Ùï‚ÊˆÇŠ‡‹U‹Óä”«”Óš†¼}½‘¾–ÐåÌeÑˆÖIÖOÚÍÚÏ¶ºäWæBî^ïŒüW" },
			new String[] { "Tu", "ÍÁÍ¼ÍÃÍ¿ÍÂÍºÍ»Í½Í¹Í¾ÍÀõ©Ý±îÊÝËÜ¢Óàƒ·ƒò†l†ž‡íˆDˆEˆMˆà‰T‰©Œ_xÄáŽêOL„’¼’Ø“\“Ÿ”¾¶Å—^™y›B›Þœ£¤¬Ÿ¯f¯…¶d¶•¹\ÄRÄ]ÇÈ‹É\ÚgµøÛTÞƒâQâŠäWäŒå„ñGòBùIùWùrùúhú“ýC" },
			new String[] { "Tuan", "ÍÅÍÄî¶ÞÒåè„Œ„–‡âˆCˆF‰t‰’‹§Œ£‘_“»¶Ø˜¤™ˆœ¨`Ÿ™ªl®™´u¶Ë°ºi¼aÉ”ÑƒÑ‰Ø‡æ˜÷Hù‡úoú™ðÈ" },
			new String[] { "Tui", "ÍÈÍÆÍËÍÊÍÇÍÉìÕß¯‚M‚QƒU†”‰‘ŠÑŒ¾wµÜL˜ú¶Ë°·~ÂvÃ“Ã•ÍÑÉ—Ë”ÌLÍ‘Öd×‚ÛƒÛ×·ëPîjîkînðÀ¡òDòoóhôs" },
			new String[] { "Tun", "ÍÌÍÍÍÊÍÎ¶ÚÙÛâ½ëàêÕ¶ÖÎâ…×†”‡pˆdŽÝ÷‘‡•H–N›Iãç›âìÀŸlŸõ®™ñ¸¼ƒ´¿ëÆÄ†Ä™ÆXÎPØZÜ”Öðå`ë˜ï‚ô÷ƒü`" },
			new String[] { "Tuo",
					"ÍÏÍÑÍÐÍ×ÍÔÍØÍÕÍÖÍÙÍÒÍÓÆÇéÒèØõÉØ±ÛçÙ¢âÕõ¢èÞö¾ãûóêíÈËûšë‚M…ï†®‡cˆ÷‹s‹µËüA±¶è’L’„’¨’É“ã–l–s—‡—ø˜’™Eš¼šÍšú³Ø›k›ñ ­ ö³a´P´u¶Ë°»X½FÃ“ÅbôªÇhÈ[ÌEÉßÏ€Ð†Ð‘Ð›Ó”ÓšÔqÕfÕh×™ËµÚ—Û|Ü€Þ~åÆÞãBälîèêeêuËåï€ð˜ñWñXñjñ„ñ…ònò™óCô…õDöz÷WørùKüƒü˜¶æ" },
			new String[] { "Uu", "ŒËÁ”É•é–G–þš`šµš¶šÄ› ŸHŸeŸ‡ H°i°›µs·EÂSÂqÂ‰Å†ÆŠÉIÉ…Ë€ÍCÎ_ÑCÚJÜxÞmåwæç”ê[ìTù" },
			new String[] { "Wa", "ÍÚÍßÍÜÍÛÍÞÍÝ°¼ÍàØôæ´ëð„¾…÷†„†œ†å†ìŠ¹‹zŒÜŽ’º“‰”…›@›AÎÛœÎj­ ®|³[·Š·“·˜¸DÂvÄeÒmÖœßœì…Ð¬ícíií€õqöÙüpü|" },
			new String[] { "Wai", "ÍâÍááËßÃ†J†·Ø²žx¸î“" },
			new String[] { "Wan",
					"ÍêÍòÍíÍëÍæÍäÍìÍåÍèÍóÍðÍñÍéÍçÍãÍïÃäÍîÂûÝ¸ëäòêçºÜ¹çþæýØàîµÝÒ^‚{Ãâ¹Ø„\…d…e†nÔ°ˆ¾‰G‰Ï‰í‰îŠ€ŠþŒRŒñŽ¦ñ­’e’Â’Ì’ç•Š•–ëÃ–v—i—µ˜´š÷›ðŸÏž³¬T±D±›¸Šóî¼w½ƒ½Œ¾O¾UÂDÃÜÈÇ{Ç|ÈXÈfËHÍWØ™ØžÙ–Ú@¹áÛlÝkÝnßà„ä[äjä‘åså†æ~évêKêPîBó[ó\ó]ô’" },
			new String[] { "Wang", "ÍûÍüÍõÍùÍøÍöÍ÷ÍúÍôÍýÃ¢éþ÷Íã¯Øè“©ƒÇ¿ïÞÌŒ²ŒµŒ¶Œ·´¸ºû’[•™–M–R—Ÿž_Ÿƒ¬]»Ê±Z¾WÇwÈDÍ^Í‡Î\ÕsÝyÞ‚ÞŽ" },
			new String[] {
					"Wei",
					"ÎªÎ»Î´Î§Î¹Î¸Î¢Î¶Î²Î±ÍþÎ°ÎÀÎ£Î¥Î¯ÎºÎ¨Î¬Î·Î©Î¤Î¡ÎµÎ½Î¾Î«Î³Î¿Î¦Î®Î­Î¼ÒÅÝÚàøôºöÛæ¸åÔãÇÚñãíçâä¶á¡áËÚóÚÃä¢ÙËâ«â¬áÍê¦è¸ì¿ìÐÛ×Þ±ðôÙÁ‚¥‚Îƒ^ƒ¤…y…°ßà††Â†Ò‡ˆàí‡úµÌ‰Š‰Ã‹W‹n‹yŒ¿ù_e™½ŽUŽhŽ®@ÎiÌÎ‘£’Ë’Ú“G“f“Ö“ã”Í•¥ÓÐ»ú—|—Û—Ü˜L™Þ›W›”›¾œwœ‘œ¿œÕ‘¬èžHžSžwžéžùŸŠŸ˜Ÿ£ìÙŸÝ V ‘ Ò àâ¢ªc¬^¬|­M­Ž¯_°I°Líõ²z³u³}´S´j´oÁ¢¾S¾“¾•¿JÁWÄ^ÆYÜÏÆ„Æ‘ÇUÝ´Ç‹ÝÌÈ”È–ÉJÉ–ÊlËeË—ÌvÎOÎTÎVÎkÎoÏGÐlÐoÒEÓAÓWÓ}Ó‚Ó„Ô•Õ†Ö^×~×ˆÚ~ÛbÛcÜZÜ^ÞEß`ßzàŒáWåMå…å—çAéÚãêžö¿ìGìSífílítí|îQï]ð]ðjðŠó[ó\ó]õKõdõnögöhöz÷˜÷" },
			new String[] { "Wen",
					"ÎÊÎÄÎÅÎÈÎÂÎÇÎÃÎÆÎÁÎÉãëãÓØØö©è·Ãâ…Ð…Ø…Ý†–‰eŠpÃä¨ëìã³‘C’^“h“‹ÃÁ•j—S˜X˜všzéâšœbœØžÉŸ±«œ¬¯‡ÑÛ³R·g·€óË¼y½ƒ¿A¿ZÀˆÂ„ÃWÃÃ‚Æ[Ç|Ê•ÔÌÊŸËœÌNÍPÎÏRÑŽØnÝ˜ÝœÞdâ†æ’éé”éšêZÏÞíyè¹î‚ðwñbô•ö€ö“÷—øYøjøsü•Ùï" },
			new String[] { "Weng", "ÎÌÎËÎÍÞ³Ýî„Ø‰RÛÕŠTÇ•²œå®Y²\ÀšÂÃÉÎŠæfúOûlýN" },
			new String[] { "Wie", "ÄŽ" },
			new String[] { "Wo", "ÎÒÎÕÎÑÎÔÎÎÎÖÎÏÎÐÎÓÙÁá¢ö»ë¿Ý«à¸ä×íÒ¥‚¬†àÉ‡f‡—ÛöˆåØ²ŠðŠñ‹_æÁ‹‹’Ó’Ú’Ü“ë”Nè»–†—ç›ð›óœuãüŸsªi­xÎÁ²Y²ˆ¸CÄOÄŸÅPÅŸÈnÉ^ËhÎÛbÛlëoñNý}ýŠ" },
			new String[] {
					"Wu",
					"ÎÞÎåÎÝÎïÎèÎíÎóÎæÎÛÎòÎðÎÙÎäÎìÎñÎØÎéÎâÎçÎáÎêÎÚÎã¶ñÎÜÎßÎ×ÎîÎàÎëåüòÚêõå»Ø£âäÚãÚùâèæðì¶öÈØõè»ðÍæÄåÃðíÜÌìÉâÐðÄ÷ùä´ÛØWÍö“Øî°Ù¨‚W‚—ƒƒÇ„„Õ…Ç…Ò†•à¸†è‡fÛëˆºˆé‰]‰ŠVŠÃŠÓŠÕ‹³ŒäŒí}¶ÎŽÄTvÓùí’šº‘“’G’H’N“h““”–ì¸•J–f–g–Ã˜îšTÄ¸›@›A›^›ž›´œrä×œ×ŽžõŸoŸ½ŸÊ«b« ¬@¬­N®Wî¦²y³J´IµµŸ·—¸PóË¹™»|ÄŠÆ•Ç`ÄªÊÌFÎÏwÕGÕ_Õ`×OÚÜRßAàNàwâEänäoåqæuèžê‚ëFë‰ëœìFìWì}ò\õˆöƒøŒùMù^úFú~ûcýHýIýrö¹ßíòú" },
			new String[] {
					"Xi",
					"Î÷Ï´Ï¸ÎüÏ·ÏµÏ²Ï¯Ï¡ÏªÏ¨ÎýÏ¥Ï¢Ï®Ï§Ï°ÎûÏ¦Ï¤ÎùÎõÏ£Ï­ÎþÎúÎôÏ±ÎøÏ³Ï©À°ÎöÏ¶ÆÜÏ«Ï¬òáÞÉä»Ýßâ¾åïçôæÒìùÙâôâñ¶ìûÙÒäÀÝûô¸õèõµì¤ðªó¬ôËÜçáãÚôßñêØó£ì¨éØãÒôÑìäêêÝ¾÷ûñÓôªÒå‚S‚`‚Ý„D…[…c…k…s…w…À…ä…è†Aß×ßÒ†Œ‡q‡›‡½‰I‰¸ŠGŠÖ‹f‹ÄŒjŒÁÊºŒÊŒÚÆíŽQŽ`ŽdK¹ÐæèïOY_¦÷ø‘‚‘ƒ‘ï‘ñ‘ò’Q’V“©Ëº“ô¼È•„•‘•Ê–y– —N—«—Ì—á˜~˜›˜é™S™úš@š]šâšãÈ÷œlÊªœëSd”•Àñž¢ŸXŸ_ŸmŸyŸ›Ÿ¼ŸÁŸçŸèŸù O x Ì × ÞªLÁÔª“«I¬N­t­Œ¯Œ±_±–²q²—´F´Ž´—·G¸O¼Y¼š½”¾k¿]¿uÀGÀMÀ{À…ÁpÁxÁ•Á—ÃZÃ[Ð²Ã{Ã|Ã~ÅbÇbÇmÈ}ÉYÉjÉtÊDÊ“Ë@ÌŸÎEòæÎ€Î‰ÏkÐPÐañÞÒuÒ‚Ò ÓBÓ}Ó‚Ó„ÔDÔqÕOÖLÖlÖuÖ×@ÚÀÚÖØGØHØgØlØ‰ÚTÚVÚiÚvÛ’ÜhßgÛ§àEàSàqá@áâMâRâlâ|ãbãcãŠåaåeæˆç^è„è•´íêSëKë^ëvëìIìUïeðFðOðqðœòwò„óNô]õ–öw÷@÷^Èú÷žùTú ü_üŸýAÛ­" },
			new String[] { "Xia",
					"ÏÂÏÅÏÄÏ¿ÏºÏ¹Ï¼ÏÁÏ»ÏÀÏ½ÏÃÏ¾»£áòèÔßÈ÷ïíÌóÁåÚè¦B‚b¼Ù‚Ò…­Ñ½ßêàÄà¾‡˜ˆYˆ®‰ìŠAá¬{BË‘³Ñº’Ü“Š”¯•g—šBì¦ë¥ä¤›ÑœÀžÙžþŸªM«”¯K¯Pðý²L³ˆ´W´lµ„¸—¹d½o¿E¿[¸øÁŽÅrÅ{ÆSÝçÊ›ÎrÎ˜Õ’ÖlØBÚYÝ àAå’æ_ç]éiépêƒê˜ïPòhô öyúT" },
			new String[] {
					"Xian",
					"ÏÈÏßÏØÏÖÏÔÏÆÏÐÏ×ÏÓÏÝÏÕÏÊÏÒÏÎÏÚÏÞÏÌÏÇÏÉÏÙÏÍÏËÏÜÏÏÏÑÏÛÏ³¼ûÜÈÞºá­ðïÝ²ôÌæµò¹áýììÙþìÞõÐõÑõ£åßë¯ðÂóÚö±™½ÁÙ‚]ƒMƒgƒmƒn…î…û†Z†m†¥†é‡JˆŸˆÉ‰A‰·Šhæ¡ŠˆŠ™æ©Š«Š·Š½ŠÒŠÞ‹M‹¸‹¹‹Í‹üå¾Œ¯Œ°ŒÝsŽMŽÒ`üã»‘a‘‘—‘œ‘¾’¦º´Ì½“`“y“{“È“Í”g”s•–}–ž—g˜˜ó™Ì™÷šÀÈ÷Ï´›×œ¶œÇ½å¥žnž¢ž¶žóªAªªž«I«N«t«ˆ¬F®Q°B°G±]Ê¡±h±•²vÒÓ³w¼î´šµUµ ¶[¶i·SóÈ¹a¹‘»˜¼`½L½m½½ž¾Q¾€¿h¿„¿ÀoÀwÀ‰ÁwÁ{¼çÐ²ÃjÃ{Ã|Ã~ÄdÅ@ÅOÅ`ÆxÇ{ÝüËWËÌ\Ì_Ì`ÍpÍ€Í˜ÐjÒDÒvÒŠÕ^ÕtÖPÖ›×]ØRÙtÚDÚ`ÛŸÜ]ÜŒÜŽÝÐùá_ázáŸãŠã”ã•ä}åDåUåvå‚çoèvîÌèïÄéeéfêRãÛêˆê“ëUí`í„í†îyî‡ï@ðWðuñMõröx÷€úNú‘ú’úšûyû’üGíéýE" },
			new String[] { "Xiang",
					"ÏëÏòÏóÏîÏìÏãÏçÏàÏñÏäÏïÏíÏâÏá½µÏèÏéÏðÏêÏæÏå÷Ïößæøó­âÔÜ¼âÃç½ÝÙºà‰ß‚íƒ¨„â„ð†“†”‡»Š¢ñŽûÝÈÁ•}•Ú–Ù˜U™ÖÑó‹«“­­˜½|¾|ÀvÀ‘ÁfÄô­ÈeËGÌZÍJÏ†ÐiÒVÔ”ÛKÞ†à_àlàmàxã}ãätç}è‚é{é•é—í‘í—ðAð‹ð“óJô\õaõœ÷P÷`÷zø—û‘" },
			new String[] {
					"Xiao",
					"Ð¡Ð¦ÏûÏ÷ÏúÏôÐ§ÏüÏþÐ¤Ð¢ÏõÏýÐ¥ÏöÏøÏùÐ£÷ÌòÙæçèÕßØáÅóãäìåÐèÉç¯óïÙ®‚PÇÎ‚j‚å„¿…®ÛÅºÅ…ëºô†DßÝÉÚßë»£†’†Û‡C‡E‡V‡Z‡[‡^‡Æ‡Ìæ¯ŠëŒnŽé–k|‘‹ÄÓÉÓ“`ËÑ“Ï“ß”¬”Â”Ã•š•Ô—nÉÒ˜þ™ÏšRšYš^š¥š®›©›ßœøx’Ížtž¼ž½žñŸ^ŸÀŸêŸò _Ø³½ÆªVª’ª”¯e¯h°~°†±³‡·n·›¹G¹q¹›ºSº}º½g½‹¿„½ÊÁ›½ºÃ‘Ä…ÉÖÜúÇzÈpÊ’Ë@ËrÌ‡ÌÏSÏ]ÏvÐDÔFÔ‰Õ[ÕqÖjÖyÖ—×DÛXÝ^ÞB½ÏàUäNïYò}ò”òœ½¾É§ófónóuø{ø“øŸújúrû^" },
			new String[] {
					"Xie",
					"Ð´Ð©Ð¬ÐªÐ±ÑªÐ»Ð¶Ð®Ð¼Ð·ÐºÐ¸Ð¹Ð¨Ð°Ð­ÐµÐ³Ð«Ð¯Ð²½âÆõÒ¶ç¥ò¡çÓâ³é¿âÝß¢ÙÉå¬äÍÙôéÇåâÞ¯õóÛÆÛÄÄ‚´‚ÄƒDƒªƒæ„µ…f…l…Ãßñà®†à‡ƒˆ•‰f‰êŠAŠGŠÀ‹rŒ@Œ‘ŒÈŒÏŒÑŒÔŒÚlŽOÇeïø’’¶½Ó“a“yß¡“û”X”y”ý•»–¤½Û˜f˜®šGì¨š¢Ö­›ªœœ¸Èœë¢ÊžažÂžàžáŸLŸcŸ» X yªn¬€íõ´cµm¶c¼I¼œ½X½e½u½’¾Š¾™ÀTÀiÀ‹Á–Ò®Ã{Ã|Ã~ÄnËZË†ÎdÎqÏÏ’Ð~ÑWÑ€ÒCÒpÓiÓnÕ™ÖCÖx×f×µýÔ¥õÍÜaåÈæEèHÚôíCíPí…í“îRÒ³÷ºôkõ@õqöÙýKý^ýaýkýšÀ£" },
			new String[] { "Xin", "ÐÂÐÄÐÀÐÅÐ¾Ð½Ð¿ÐÁÑ°ÐÆÐÃì§Ø¶Ý·ïâÜ°öÎê¿²¿‚rÐË‡Œ‡Š|Š®‹×ŒJŒ¤ŽßQ¹×‘€“Ú–‚–“™AšE¿îšL€žÔŸ{±^µUÃ’ÅdÅgÒWÔDÔMÜŒß”á…âdä\ç†êcîˆñQñ^ôg" },
			new String[] { "Xing", "ÐÔÐÐÐÍÐÎÐÇÐÑÐÕÐÈÐÌÐÓÐËÐÒÐÏÐÉÐÊÊ¡íÊã¬ÜþÚêß©Üôé‚††Qˆgˆlˆž‰DŠÈŠü‹”‹ñŽy•Û›™›ëœîŸ“ŸÉ õ¬w°‹²MÑÐ³x¹“¹ž¾mÊ¤ÅBÅdÇnÍÑRÓqÓwÖ_àDâ]ãoã‹ätè—è™ê€ðhâ¼òHóUõSö]" },
			new String[] { "Xiong", "ÐØÐÛÐ×ÐÖÐÜÐÚÐÙÜºƒ´†M‰éÏÜúr”¸•d›°Ÿ‚ÃrÔKÔwÔž×›×œÙ‚ÚU" },
			new String[] { "Xiu", "ÐÞÐâÐåÐÝÐßËÞÐáÐäÐãÐà³ôäåõ÷âÊ÷Ûð¼ßÝâÓá¶‚c‡›˜¼™Ïœúžñžòìã«‹¬L­P¼N½½‘¿ÀCÃƒÃ‘ÅWÅ^ÆvÆ’Ý¬ÉŠÎÑfÑ„Ñ…ã–äPæTæ™çVçnïqð}ó…õx÷Gø ýM" },
			new String[] {
					"Xu",
					"ÐíÐëÐèÐéÐêÐîÐøÐòÐðÐóÐõÐöÐçÐìÐñÐ÷ÓõÐïÐôÐæôÚÛÃèòä°Þ£çïÛ×äªñãõ¯Ú¼äÓìãíìí¹ÓÚ€­ÐÝÅÓàò‚T‚»ƒÛ„Ô…rÅ»…éºôßÝ†ÄÐá‡I‡b‡uˆ¦‰ÙŠˆŠ½ã‹€‹ÁŽ­Vâðj±‘A’î“T”›”¢ê¸•B•d•v• •ýëÔ—ì˜™øšAšHš[š_š~›T›UœMœ•œäGs…žíŸTª««—¯L±N±S±r²W²x²œ·P·V· »n¾A¾w¾{¾–¿HÀ]ÀmÂ…ëÉÃaÓóÆRÆ^É[É’ÊŒËvË…ÌÌ“ÍmÍ‚ÎdÒŽ¹æÓ’ÔSÔ[Ô‚ÕšÖ[Ö~ÖŽÖžÚ©× Ù[Ð°à†ã_ã„è`ö§íšíœÙåòôPôqôzôˆ÷r" },
			new String[] {
					"Xuan",
					"Ñ¡ÐüÐýÐþÐûÐúÐùÑ¤Ñ£Ñ¢È¯êÑé¸ÙØäÖäöãùîçè¯ìÓíÛïàìÅÞïÝæðçÚÎØ¨…º†I†¿‰H‰éŠˆŠ®‹l‹Ÿ‹Ö…RËÐ‘¤‘Ò“E×«ß§•R•]•t•œÅ¯•Ã—]˜C™eä­ä¸×Ÿ@ŸœªBª™«R«t¬I¬K¬u­v­‚°_±P±†²U²¶P¹Ž½L½k½¿h¿¿’¾îÂAÂQÈkÉ{ÊRÊžÌBÌTÍ•Í›ÎhÏÐfÐžÕÖX×X×zÚKÜŽÞFßxß€ãCämæMæ›èGéIìœïXð‚âÍñòCö~" },
			new String[] { "Xue", "Ñ§Ñ©ÑªÑ¥Ñ¨Ï÷Ñ¦õ½àå÷¨í´l¾ö„ä…ÉÏøàëˆy‰®ŒWŒúNŽGV”Ä–ù—]˜Ý›Q›‡›‰œéÍžyÈ²žû K ü¯N¯T²xÄ}Å–Æ‹ÉHÐÓ{ÖoÚÊÚpÞGÞjëzíYí|÷Lú›û`" },
			new String[] {
					"Xun",
					"Ñ°Ñ¶Ñ¬ÑµÑ­Ñ³Ñ®Ñ²Ñ¸Ñ±Ñ´Ñ·Ñ«»çÑ¯¿£Ùãöàä±Û÷âþâ´õ¸ä­Û¨á¾Þ¦Þ¹Ü÷ñ¿êÖáß¾ù‚Å„×„ë„ì…_†C‡e‡x‡ ˆ_‰_‰¶‰ËŠQŠ®ËïŒOŒ¤eãªô“M“Í”–h–Õ—DËó˜ßš¦š½È÷Ì¶¡žFžµŸ[ŸŸïŸñ @ ` o wªFâ¡«‘­R±†²†¶¹S¹oºJ¼r½kÀcÑ¤ÄÝ¡ÈÊMÊnË`ÌQÏrÏyÒWÓÓ–ÓœÔƒÙbÞ™åÒ¶Ýßdà‰èRöÎîšñZñ÷S÷\úZåæ" },
			new String[] { "Ya",
					"Ñ½Ñ¹ÑÀÑºÑ¿Ñ¼ÔþÑÆÑÇÑÄÑ¾ÑÅÑÃÑ»ÑÈÑÁÛëñâí¼çðèâíýæ«ðéá¬ë²ØóåÂÞë^„†‚o‚œ„²…|Ñá…ƒ…’ß¹Îá…ì†s†¡ˆBˆLˆRˆºˆ×ˆÛ‰ºŠ´‹IŒSŽŽÞŽâÓù’~’¥’éÔý–‘—¿—âÐªšå›ÅQ”žõ ëªcªm«e¬ˆ¯P¯{íÙ´l¶–·Š¸E¸ŽÂyÊ‹ÒÓ ÜˆÝ`Ý‘éûÐ°âXåEçŒè›élÕ¢î†ÑÕøfø†ùgùsù“ðÆýGý\ý…ÑÂ" },
			new String[] {
					"Yan",
					"ÑÛÑÌÑØÑÎÑÔÑÝÑÏÑÊÑÍÑ×ÑÚÑáÑçÑÒÑÐÑÓÑßÑéÑÞÒóÑËÑâÑãÑäÑåÑæÑÑÑÜÑèÑàÑÕÑÖÇ¦ÑÉÑÙÜ¾ØÉãÕÝÎ÷ÊçüäÙìÍØÍóÛëçÙðØß÷Ðâûî»éÜäÎÙÈÚÝëÙêÌãÆÙ²Û±õ¦Û³åû÷úáÃÚçæÌmµ«°³‚©‚¹ƒBƒ°ƒ¼ƒÒ„‰…]³§…y…’…—…˜†m†Ç†Í‡{‡²‡À‡ÙÛïÛû‰c‰†‰Á‰ÌÏÄ‰üŠzŠ°Š¶ŠÔŠ×‹j‹Ç‹Í‹é‹÷ŒEŒß°»¼öŽMŽiŽrŽsŽtŽvÑ²¹ãâÖem©Ý‘±‘î‘þ’Z’¨’´’É’ï“C“RÞî”©•V•••¶•à•ó—¦—â—ã—ð˜Ü™L™•™¿™ëš‡ë³›Wä¦›¡ÏÑ›þµ­Òùœ{œœ¶œÄvž ž¥ž·ž¹žÏŸSŸgŸŸŸÌŸð w ²ºÝªPª_«Š¬J­’®[³x³Ž³š´Nµhºcº™½ž¿tÏÛÁwÄdÅEÆFÆGÇrÝ²ÈCÈTÈ€ÈŠÊBÄèËWÌšÎiÑsÑŠñûÒÓ_ÓƒÓ…ÔPÔÖV×…×—Ú¥ØVØWØ]ØbÙžÚIõÂÜyÜ‚Õâß@ßVàIàáDáZázá€á‰ãUåUïÄéZéŽéé‘êmÏÕêŽêšëCëUØÌìvîîƒî†î›ðòVòYòzòžóFôeô|öoøHøNøeø‘ùžú`úŽû}û’ûšüGüdüfüiüjükümüsýBý]ýdýzý‡ýŒ" },
			new String[] { "Yang",
					"ÑùÑøÑòÑóÑöÑïÑíÑõÑ÷ÑîÑúÑôÑêÑëÑìÑðÑñì¾í¦áà÷±ãóòÕìÈâóÖ‚ê„½„Ø…n…óˆtˆ”ŠIŠš‹PŒ¢Œ÷¤§û‘Ä’t“P”a”®•D°º•[Ó³•ª–³—î˜D˜”˜ÓšTšÞšçœ«‹žYžæŸ¬«Œ¬„¯ƒ°W±j±ˆµS¶@½DÁfÁkÁnÁyÃoÓ¢ÔhÔ”ÖUÏêÝIÝŒãZå}åç{è–êgê–ë‡ë›ìRï^ïrï…ðBñöuø„ø—ûF" },
			new String[] {
					"Yao",
					"ÒªÒ¡Ò©Ò§ÑüÒ¤Ò¨ÑûÑýÒ¥Ò£Ò¦ÑþÒ«Ò¢Ô¿½ÄÌÕÔ¼Å±çòØ²÷¥ðÎé÷Ø³ßºï¢çÛáÊëÈê×áæèÃñºÃ´ÀÖ¦‚x‚¶‚çƒe„üÄö†º†Ú‡y‡§ˆˆòæ¬‹Q‹„‹ÆŒaŒ¸ŒëiŽAŽCÓ×ÙQfç’q“e“u“Á“ê•¬•ê–”–Ì—ê˜e˜l˜·š|š¥ä¬ÒùœÈœôå®žìž÷ŸÆ d úªqªrª’«Q¬ŽÓÉ±l²‡´tµn·Ž··š¸G¸H¹O¹–¼sôíÀfÅ—ÆwÜéÈ™É@É|ÊËaËŽÌiÐ‰ÒÔ@ÔoÖ{Ö|×ŠÚŒÛuÝUßbã“æcè€é™êœì‰î–ï_ïuïŸðPò[òˆæñöŽø^ø€ú_úrýGýo" },
			new String[] { "Ye",
					"Ò²Ò¹ÒµÒ°Ò¶Ò¯Ò³ÒºÒ´Ò¸Ò±Ò­Ò®ÑÊÒ·Ò¬Ð°ÚËÚþêÊìÇÞÞîôØÌˆ‹‚œ‚´ƒp…½†œºÈ‡S‡™ˆ¸ˆìÊû‰¢‰­ÉälŽIŽJ‘±’w×§’À’ÅÞîÞé“ü”@”I”K”LÐ±•¢•Ï•Ð•â–‘–¥–¦˜G˜IšSš†š‡›ä¦Í¿c£ÖŸ¤ŸºŸî { ”­’¯u°‡²w²|µB¸E¾ŠÄôÂ™Ý±È~ÍFÐJÐMÖ]àvà’âPâXäyæEæUædèHìví“ðNðYðvð†âÅñ@óBùwûEüg" },
			new String[] {
					"Yi",
					"Ò»ÒÔÒÑÒÚÒÂÒÆÒÀÒ×Ò½ÒÒÒÇÒàÒÎÒæÒÐÒÌÒíÒëÒÁÉßÒÅÊ³°¬ÒÈÒÉÒÊÒËÒìÒÍÒ¼ÒÏÒêÒ¾Ò¿ÒÓÒîÒÕÒÖÒïÒØ¶êÒÙÎ²ÒÛÒÜÒÝÒÞÒßÒÃÒáÒâÒãÒäÒåÒÄÒçÒèÒéâøðêï×ñ¯âùæäì½ìÚôýñ´ì¥á»ÛÝéìàÉß×Ü²àæØæÚ±âÂäôØýßÞðùâ¢íôôàÞÄÜÓÜèØîÙ«êÝîÆçËåÆØ×ã¨÷ðôèß®ÞÈòæÛüÞÚáÚÞ²ß½éóïîô¯Ø¯UVÒ²{±ÊÌÙ¢î‚X‚Ãƒxƒ|ƒŒƒÏƒÞ„·„Ö„ã„ù…FÓ¡…¥…¬Ì¨Ì¾…ÀÌý…å…ê†jÒ­‡ÒˆIÛÙˆ`Ûçˆ£ˆË‰©‰Ò‰ßÏ¦Ê§ÆæŠ‰¼§‹f‹‹¡‹Â‹Î‹ÚŒTËüŒbŒhŒ•Œ–Éä[Æé©ŽFŽKËÈŽƒŽ•Ž–Ž¯ŽåMo‚£¤¥±æ@µ¡q–‘›‘«‘÷‘ü‘ý’LÔñ’Þá’íÞõ“~“Ì“ñ””§”¹”¾Ê©”î•i•”••Ë•Ù•ö–p–s––¤–ª–Ø–å–ñ–õ—©—×—à˜]˜®˜¯˜à™j™}™™˜™ö™ýš]šcš…š¡š­Æû›nÖÎÐ¹›u›¥›ªäª›¶›Å›ÎäÍœ™ÉÛž‹žËÑÉŸyìÍŸ|ŸÁÎõŸÖŸÛŸé D J Wê÷ ô öª~«p«}­C®A®Š®¸í¯m¯Ž¯–²G²eíÒµEµKµt¶B¶h·F·j¸v¸”¹•ºm»J¼œ½X¾S¾_¿O¿ˆÀXÀ[ç¥ç²Î¬ÁpÁrÏÛÁwÁxÂ]ÂkÃEÄjÅ’ÅœÆNÆiÆqÈUÈ^ÉšË„Ë‡Ì[ÌˆÍ~Í‚Í†Î’Î•ÎœÏñÂÐtÐzñÇÐ„Ð‘ÐšÑ`ÑvÑ‹ÒAÒ]Ò~Ó~Ó”ÔTÔUÔmÔqÔrÔ„ÕBÕOÕxÖuÖ–×b×g×h×r×‚×”ÚÀÚÖØ[Ø\ØsØŠØ—ÙOÙŒÙ“Ú˜ÛDÛpÝWÝrÝ}ÞTÞjÞvÞ~µüÞÞ–Þ ßzàcátáyá{ÊÍáŒáâNâPârâzãAãBãiãŽãžåWæ„çFèOèîèèŸêdêeêuê‹Á¥ëcïôëìJìaìˆí›îUîVî{î‰ïð†ñkóAó`õkõlöGøCø˜ù€ùù‹ú^úgúsú…úœû@ûkûoûpü]÷îüpýtý~" },
			new String[] {
					"Yin",
					"ÒòÒýÓ¡ÒøÒôÒûÒõÒþÒñÒ÷ÒüÒúÒðÒùÒóÒöÑÌÜ§Û´à³â¹Ø·ö¸ßÅÛßáþÛóö¯ò¾ë³î÷ñ¿ñ«ä¦ÜáZ]µÖÚðƒBƒÜƒøÌý†‚‡w‡‘‡¨‡à‡ôˆŠ¿Ñˆ¤ˆøÒ¼‹A‹H|•ŸŽ\±ÒJÖ‘@‘\‘€‘‘¶”Õ–@–ð—V™a™ƒ™’™Ó™ýšJšPš’ÒÊ›Ž›ä±œ^œšœÞœôÌ¶¡þž@žô ìªZ«l«­K¯Š°E°a³w´H´€µš¶†¹N»ƒ½s¾ž¿tÜËÆgÇZÉMÊ_ÊaÌaÏPÏrÑPÑÔÓ—ÓÔCÔDÕzÖN×Ú_ÚyÝláDáSâYâiâwãyãŸéœéžêfêŽê”ê›ëLë[ë–ë ì‚ìíï‡ï‹ñ—óSõgúý]ýlý‡" },
			new String[] {
					"Ying",
					"Ó¦Ó²Ó°ÓªÓ­Ó³Ó¬Ó®Ó¥Ó¢Ó±Ó¨Ó¯Ó¤Ó£Ó§Ó«Ó©ÝÓéºÝöñ¨ÜãðÐëôÝºè¬Û«àÓÞüçøäÞäëÙøó¿å­âßÜþò£‚\‚ŸƒOßì†¦†Ó‡|‡Â‰L‰àÑë‹k‹”‹ë‹ýŒ[ÊŽcŽg_êá‘ª“²”l”t”w•@¾°•£–P–³—@—H—w™Ñ™Õ›s›Æœ€œ»œÁœî}õöžLž]žuž„ž‰ž¡ž­Ÿ‚Ÿ–ŸÉ I«›¬“­‹®O®Zµé°`°Ÿ±j³A´Qµ_·f»Y»k¾x¿I¿MÀKÀtÀ†ÉþÀ”À›ÂmÄ{ÇoÈtËpÌcÍwÎsÎ„ÎžÏ‰ÐNÑšÓLÖh×G×sÙaÚAÜ…³Ñævçè]éAë›ì™í‹íŒîeïI÷jøŠúDúLúˆú—ûKûW" },
			new String[] { "Yo", "Ó´Óýà¡†Ñ‡©" },
			new String[] { "Yong", "ÓÃÓ¿ÓÀÓµÓ¼ÓÂÓºÓ½Ó¾Ó¶Ó»Ó¸Ó¹Ó·ÓÁÛÕã¼Ù¸Ü­÷«çßà¯ð®÷ÓïÞ‚æ‚ò„Ê†Þ‡‡ˆ¬‰M‹£ÈÝÓb­[~¾Óòú“N“í–º–Ô˜Ÿœ¥KÑžœ œ°M°b³l³‹¶H¹cô§ÆoÉKÞ³ÔÛxÓöàaà{ákäVçOëtî„ïJõ—ö÷I÷‘úx" },
			new String[] {
					"You",
					"ÓÐÓÖÓÉÓÒÓÍÓÎÓ×ÓÅÓÑÓËÓÇÓÈÓÌÓÕÓÆÓÊÓÏÓÓÓÔÓÄðàØüòÄÝ¬öÏØÕ÷îÝµéàòÊå¶ë»àóèÖòöÝ¯÷øîðòøôíßÏÙ§JŒ‚ºƒžƒÜÌ¾†N†e‡¦Ûê°ÂŠmŠµŒMÞÌŒ²frŽîJMQ‘n‘É‘îÈÅ“AÞí”å–ë—X—`˜A˜©™¢™Ôšü›S›YÇö›w›|›Áœ±HžX ¨ ¶ ûªq«D®h¯_µvµ™¶x¼nÀlÁgÁhÁmÂiÂuñúÃUÃ…Ã‘ÆhÇxÉKÊ~ÍYÍœÑ„Ñ…ÔIÕTØzÝjÝ’Þ”ÞœßKß[ßˆà]à›áRâ™äBäPÐâñfôœõO÷†û~" },
			new String[] {
					"Yu",
					"ÓëÓÚÓûÓãÓêÓàÓöÓïÓúÓüÓñÓæÓèÓþÓýÓÞÓðÓÝÓéÓÙÓßÓìÓíÓîÓØÓáÓâÓòÓóÓô¹ÈÓõÓÛÓ÷ÓøÓùÓäÖàÓåÎ¾ÓÜÓçÔ¡Ô¢Ô£Ô¤Ô¥Ô¦ÎµåýáÎö§âÅãÐñ¾ðÁæ¥ÞíñÁêìô§ô¨ö¹Ý÷ìÏîÚÚÄæúì¶óÄè¤Ø®í²ì£Ù¶ØñàôðÖâ×êÅÝÇðõÚÍå÷àöðöìÙâÀØ¹ìÛëéáüÝÒòâòõ@¿÷€­‚R‚q‚¦‚øƒhƒ™ƒÊ…P…°…ÇÎáà¡†‰à¯†³†¸†É‡oàÞ‡‰ˆSˆÖˆèˆï‰¥Ï¦°ÂŠÊŠÓŠØ‹U‹V‹‹ž‹äÍðŒ†}áÈ£·îŽZŽ÷ªóƒ„­±‘j°Ã‘µ»ò‘í’G’TÞÖ’§’À” ”Ë”Ñ”ùê¼–fèò–ë–üÎà—™—š—§—å˜@˜K™È™ä™óšQšešu›@›AÎÛãéœMœUœŸœùOÁÄË°ÄžºžÁŸ~ìÐŸú V Œ ¢ªzªÍõ«]«_¬Z¬^¬r­m®Œ®¯_¯°K±E²I²œ³_³†´›µHµNµ€¶R¶r·C·U·{·‹¹zºh»B»Z»n¼u¾s¿›ÁNÁ|Á”ëòÅcÊæÅ„ÆRÔ·Æ‘ÆœÇSÝÎÈgÈhÉfÉ™ÊvÊšÊ ËvÌPÌ]ÍGòÜÎCÎƒÏLÏXÐjÑÃÐsÑ@ÑˆÓDÕZÕ˜ÖIÖ~×uØ‚Ø…Ø‹ÛuÜ†ÝhÝ›ÞXÞ}ßNßyßŽàNàháCáqâDâ•ãƒä`äoå[å“ç~çŸèžé‘é“ãÕê|êœëTëkìMîAîYî„ïJï„ðNð|ñSòeòó^ókôcôdôrô~ôˆõ‚öVöi÷N÷rø\øƒø…øˆùOú}ú–ûCûOû‡ýrý{í±" },
			new String[] {
					"Yuan",
					"Ô¶Ô±ÔªÔºÔ²Ô­Ô¸Ô°Ô®Ô³Ô¹Ô©Ô´ÔµÔ¬Ô¨Ô·Ô«Ô§Ô¯à÷ö½éÚæÂë¼íóð°ÞòÜ¾ãäè¥ó¢óîÜ«Ûù‚ÓÔÊ„u…Œ…™ÑÊ†T†¿‡…‡ä‡ûˆ@ˆA‰íŠ€Š†‹…‹‹ ‹õÍðŒw…€­¾è–z—¥˜C˜g˜r™´ä¸›ðœYœaœeœmœ®œÆž”Ÿ]ªjªx±\µž´©¸¾‰¿FÁ~ÃOÉAÉVÉdËQËeÍWÍ›ÎQÎmÎzÐcÑjÑrÑ†Ñ“ÖwØ’ÚOÝkÞ@ßRßhß‡ß–áJâƒä‘æ…ÈîÔÉëEîŠñrò{ô’øSøxùtù úMûgüxüŒü" },
			new String[] { "Yue",
					"ÔÂÔ½Ô¼Ô¾ÔÄÀÖÔÀÔÃÔ»ËµÔÁÔ¿å®îáë¾ÙßèÝéÐßÜ†d‡‚àî¿éåùŠx‹íŒéŽ[¦§x‚‘à’F’`’Õ”^•õ–†™µšõË¸Ÿ] q ~«h²ˆ³Eµj¶^¹–ºM»C»a»l»›¼sÄŸÒ©ÌgÍQÍRÍ‘ÍÉó¶ÕfÕhÚŒÚ”õÈÜSÜVÜ‹â_ãXäJä„èpîåÈñé†é‡ó–ûNûVügý›" },
			new String[] {
					"Yun",
					"ÔÆÔËÔÎÔÊÔÈÔÏÔÉÔÐÔÅÔÌÔÍÔÇÔ±ëµã¢ã³Û©Ü¿óÞè¹êÀáñéæç¡ìÙ»‚Ö„ò…°…Ô†T†½‡ç¾ùŠ@Š[ŠuæÁ‹‹Î¾ÒüÔ¹Áä‘C’d’l•ž–—˜X˜·˜øšŒšè›V›â›éÎÂœÝ·ŸŸ±Ÿ¸Ÿ¾ŸÂ«j®sÎÁ¶Ü±d´p¶n¹S¹oºJ¼‹¾¿A¿Z¿aÀIÀˆÂmÄZÔ·Ç\ÝÒÉCÉQÉlÊ|Ê•ÊŸËœÌNÎQÎ‚ÑŽØ’Ù„ÙšÚOÚSÝ˜ÝœÞdß\àiàyádájâqä]êmëEë…ìBíríyíîfðañNñaýqýy" },
			new String[] { "Za", "ÔÓÔÒÕ¦ÔÑÔúÔÛßÆÞÙ†‘ßý†¹àÒ‡m‡Í‡Ô‡ÙŽ‰–ý›e›jžUž£´’Ùá¼’¼™ÅHÅNÒSãNëjësë{íˆô˜" },
			new String[] { "Zad", "•õ" },
			new String[] { "Zai", "ÔÚÔÙÔÖÔØÔÔÔ×ÔÕçÞáÌ×Ð‚îƒ„²Å’D›’œ…œÖžÄžü²P¿fÇÙ†ÝdáP" },
			new String[] { "Zan", "ÔÛÔÝÔÜÔÞô¢ôõôØè¶ÞÙêÃöÉ‚Ìƒ›ƒ­ƒ³†¹‡ÔŒv“S“Ë”€•º–ýä¹äÕžRžUž£­­‘¶`·‰ºdºÅNÒ{×{×“ÙmÙÚŽÛŠà™àŸáAçYçZç‡ç‘ð•" },
			new String[] { "Zang", "ÔàÔáÔß²ØÞÊê°æà…M‰ZnãÞ ™ ³ÁnÄ ÅKÊiÙ_ÙjÚEÚNäQñzóGóv" },
			new String[] { "Zao", "ÔçÔìÔâÔãÔîÔïÔæÔäÔêÔåÔíÔëÔèÔéßð‚ó†r†×‘V–Ò—_——²ÛŸ¯­F­b°o½Ñ¸Y¸^ºr¿‰ÀRçÒçØÅ²ÝËkÖ×YÚ‹Û›ásè" },
			new String[] { "Ze", "ÔòÔðÔñÔóÕ¦²àóåô·àýåÅßõØÆê¾óÐØÓÀ‚È„t²Þ…‹†‡†¨‡K‰÷‹¨¡Ž¾ŽÙŽú’k’¾´ë“ñ•W×õ˜Áštšò›g›zœÚœõÉž•°ƒ²G²c²ž´Ÿ¶ð¢ºjÂdÈ[ÈyÊjÌEÏÒ]Õ‹Ö†Ö‰×yÚØØŸÙ‘ïŽûBý`ýv" },
			new String[] { "Zei", "ÔôÏŒÙ\öaöf÷e÷Œöê" },
			new String[] { "Zen", "ÔõÚÚÙÔ‡×“Ë×P×U" },
			new String[] { "Zeng", "ÔöÔùÔ÷Ôø×ÛçÕîÀêµï­‰ˆ•û™IŸå­Q³D´Œ¾C¿f¿•ÖŸÙ›à‹ä{ôi÷_" },
			new String[] { "Zha",
					"ÔúÕ¨ÔüÕ¢Õ£Õ¥Õ§ÔþÕ©Õ¡ÔýÀ¯²éÕ¤Õ¦íÄðäß¸ßîé«âªòÆÞêà©×õßå÷þ‚´‚¼ƒÔ²á„‘„ž…~¶ß†Æ‡ÍŠLŒoâô’K’s’€’Ÿ’·²å“c“ƒ“’“«“ü–¼–Å˜ÏäÍœÑ¤žÁŸ¤ £®h°•°šóÐ¹€¹†¼’¼™ÂdëúÆzÜÚÊPÊxË ÍlÓuÔpÕ‹ÖŠ×A×QÛzÛ‚ÜˆÞámåŽélëíCõWõ~öl÷‡÷öøýOýeývö´âÇÔû" },
			new String[] { "Zhai", "ÕªÕ­Õ®Õ«Õ¯ÔñµÔÕ¬²à¼ÀíÎñ©…‚È‚ù…z…~†‡‰ã¶È’n’Æ“ñ”`”È²ñ˜z™y ¹´Ãóåºj»yëúØŸÔðãSñ~ææódýS" },
			new String[] { "Zhan",
					"Õ¾Õ¼Õ½ÕµÕ´Õ³Õ±Õ¹Õ»Õ²²üÕºÕ¿ÕÀÕ¶Õ·Õ¸Õ°ÚÞÞøì¹×‚·‡~‹¶®ãäøŽEG¬‘é‘ð’€”Ø”ö–î—C—£—ä˜^˜ö™ÙšÖšØäÕå¤¬W°œ±K´D¾`Ç•ËUÌ›ÌœÍtÌ»ÒfÓOÔaÖt×`×d×–Ç«ÚjÛ@Û…õðÝuÝšÞJßá\õ´énë•ïQïsïðeðŒò–ò æöô}÷gø@øZûDûrücür" },
			new String[] { "Zhang", "ÕÅÕÂ³¤ÕÊÕÌÕÉÕÆÕÇÕËÕÁÕÈÕÃÕÄÕÍÕÎÕÏØëæÑá¤Ûµè°áÖâ¯ó¯Ÿƒ@‰zŽ¤ŽÇ{ˆ‘P’E³Ð•À›îq¯o¯“²d´˜»w» Ã›ÉŸÙ~ßlçbç•éLéMì ð\ò†÷Jû–" },
			new String[] { "Zhao", "ÕÒ×ÅÕÕÕÐÕÖ×¦Õ×³¯ÕÑÕÓÕØ³°ÕÙÕÔèþßúîÈóÉÚ¯Ô†ˆŠ„Ž‚”íêË•×––ÌÒ™˜Ä×åªžÝ Y ªD¬°œ±@²¸S¹|À’Á^ÃAÃDÇŸÖøÔéÔtÖšÚwá“âWãDå™ñqõeøJü{ü…" },
			new String[] { "Zhe",
					"×ÅÕâÕßÕÛÕÚÕÝÕÜÕáÕàÕÞÕãèÏéüô÷ß¡ðÑíÝñÞòØÚØØ±š…z†£†´à¿†øàÖ‡¬‡Ëˆ³¶Â‹«Êü†‘e’V“”Éã”z³â•†•‡–l—‘˜µ˜ÎšyœJž³K³Y»q»„ÞÇÄôÂzÂ™ÍEÏUÏVÐŸñÒÒxÔ€Ö†Ö‘Ö•×y×„ÝWÝmÝtÞHéóß@ßmäOæNÚîñXóCõ„úpúvðºÖø" },
			new String[] {
					"Zhen",
					"ÕæÕóÕòÕëÕðÕíÕñÕåÕäÕîÕïÕçÕèÕéÕêÕìçÇÝèìõóðéôé»ð¡êâëÞð²ëÓä¥èåî³ÛÚé©‚E‚É´½Ûã‰\‰`ÌîŠª‹ŒzŒÇŽžÉ÷ê¬’r’™“L“Ž”ž”´•_–b–ž–×–Ú—F˜E˜^˜ˆ˜çš‹›l›mœäÚµá›Øª€«‚¬‘±p±w±‡´Uµ¶G¸t»E¼…½G½„¿b¿jÀƒÂrÈZÈœÉRËmÍ–ÐÑ]Ò˜Ô\ÕgÖnØ‘Ùc³ÃÚfÝFÝŸÞtÞßZáGáIá˜â\âœä‹ågåŒæPææ‚ê‡êìkñ}ôIõa÷yößøcülüm¶¦ü‡Ö¡" },
			new String[] { "Zheng",
					"ÕýÕûÕöÕùÕõÕ÷ÕúÖ¤Ö¢Ö£Õü¶¡ÕôÕøÕþá¿îÛï£óÝÚºáçöëØ©ØöÍ‚t„Jˆ½ˆÁ‰^Š’‹o”˜òŽ¬»ÑÕñ‘~³Ð’c’ê’ð“@“Õ³¨•“Ö¹šé›ÆœžÚŸA Žªbî®°Y±k± îª¹~ºP¼l¾PÂtÃwÔ^ÕŠ×CÌËÛtàã`åPô@õSõ›ö]øgÖ¡" },
			new String[] {
					"Zhi",
					"Ö»Ö®Ö±ÖªÖÆÖ¸Ö½Ö§Ö¥Ö¦ÖÉÖ¨Ö©ÖÊÖ«Ö¬Ö­ÖËÖ¯Ö°ÖÌÖ²µÖÖ³Ö´ÖµÖ¶Ö·ÖÍÖ¹ÖºÖÎÖ¼ÖÏÖ¾Ö¿ÖÀÖÁÖÂÖÃÖÄÊ¶ÖÅÊÏÖÇÖÈàùÞýíéèäè×éòâåìíòÎëùö£Û¤èÙåéÜÆìóåëðºôêõÙëÕæïéùðëÚìõÜïôÛúêÞØ´õ¥õôõÅèÎv~¼¿‚f‚u‚Ž‚À‚Ðƒœ„M„Œ„¬„¶…„†A‡¢ˆ^ˆpˆ€°£ˆÌ‰y‰~¶à‰ïŠ‰Š©ŠÍ‹q‹ÀÊµŒ…ŒªŽŽŽŽÃŽæŽèD¼¿ÃÕáçÊÑd‘e‘p‘Á‘Æ‘ç’W’X’nÍØ’†’”’Ã“w“ˆ“Ÿ“¯“´”S”T”`”Õ”òêÇ•y–s–y–»–ñÔÔ—d—„—Ð—ù˜u˜—˜µ˜Þ˜à™£™±šlØµ›D›E›b›‚›œ›±œ]œíœþZ†žž\ŸÜ ÃªOªa­}­•®‡¯F¯U¯W¯€±‚³UµYÊ¾µoµwµ…µ•¶A¶_¶h¶o¶q¶~»ý¶ƒ¶ˆ¶ž·W·a¹e¼ˆ¼•½‚½¿@¿{¿—Á“êÈÂpÂšÃeÃqÄˆÅ\Å]ÆWÆ‡ÆÇ ËSËŒÌuÌŒÍVÏHÏdÐ}ÐÐ—Ð˜ÑuÒjÒžÓdÓhÓzÔJÕIÖ}×RØTØUØ Ù|Ù—ÛNÛyÛÛ•Û—ÜUÜWÜÝTÝXÝe³ÙÞŒßgßtÛªáBá™âŸã‡äKäkèeÌúèœé@êeênêuë\ëbñ\ñcñ‹òsòòŽöSøFøTøvøúEúvð¯ü~âº" },
			new String[] { "Zhong", "ÖÐÖØÖÖÖÓÖ×ÖÚÖÕÖÑÖÒÖÙÖÔõàô±ó®ïñÚ£âìZ«‚£„d†Áˆú‰VŠqŠt‹gŒ»Žº³×–°šp›O›wäüžÆŸŽ ð¯~±Šµr·N·rÍ¯¹W»b½K¾…Ä[ô©Æ ¶­ÊWÍ\ò¼ÎuÎ ÏxÐ\ÐxÐ{Ñ~ÖAÛ Þ‰â`â{äVæRçŠø‚ü™âº" },
			new String[] { "Zhou",
					"ÖÜÖÞÖåÖàÖÝÖáÖÛÖçÖèÖæÖßÖâÖãÖäôíëÐæûÝ§ßúíØç§ô¦æ¨ôüÆÙªúÙÃƒuƒÙ…â†B×Ä†µà¹‡€‡œ‹B¸®ÅÅ¤’ô•ƒ•ŽèÖ—¹×¢›œ@žëžö«‰®L¯J°™±T²H³B×£¹»N»Q»‹¼q¼—¿UÁŸÃiô¶ÈFÈ’ËgÔkÔ—Õ{ÕŒÖa×p×žÚÁµ÷ÙkÚQÝSÝcÝqÞbÖðßLàXâ™ã{ÓËë“ñtñ™òLò|óEæãå÷öBù@ûb" },
			new String[] {
					"Zhu",
					"×¡Ö÷ÖíÖñÖêÖóÖþÖüÖýÖöÖô×¢×£×¤ÊôÊõÖéÖõÖëÖìÖùÖîÖïÖðÖúÖòÖûäóä¨ØùðñôãÜïÜÑéÍô¶èÌóçìÄÙªîùðæä¾ñÒõî÷æÛ¥éÆóÃÓèÐ„Ÿ„±„¸†B†ø‡€‡Úˆ|‰£‰ÔŒFÄþŒeŒ¥ŒÙ­Êü“o”±”½¶·”á•ô³¯–’Äû–Çèú˜Ö™·™½™Á™îšŸ›{Å¢äøîžzž¯žÛŸ— T ‰±v²š³d³p´„µ‚¶‹·”¸m¸‰¸˜¹hºBºZºa¼Ÿ½A½ZÀ‚ÁCÁqÆ^Ær×ÂÇAÇdÉÊxË\ËŸË òÄÎwÏŽÐEÐWÑNÔ]Ô}ÕDÖTØiÙAÚŸÛBÛHÝOÞŽ¶ºßIãIãLãäŠèTè“×è³ýê•ë—ïŒñ[ñvñ–ò|æãõfö^÷Eø–úžû„ü}ÖøØ¼" },
			new String[] { "Zhua", "×¥×¦ÎÎ“«“ë™tºœÄó˜" },
			new String[] { "Zhuai", "×§×ªŒ¾’Å±‘ÛJî“àÜ" },
			new String[] { "Zhuan", "×ª×¨×©×¬´«×«×­ò§âÍßùãç‚÷ƒQƒ]„–…¡‡Êˆæ‰t‹§ŒNŒŸŒ£wÞÒ“»ÍÄ`žÀ¬ƒ­A®U´u¸|ºeºiº‹»M¿xÂZÄRÄxÉEÏmÒNÖK×NÙÜžÞDàî…ð‚÷H" },
			new String[] { "Zhuang", "×°×²×¯×³×®×´´±×±Ù×ÞÊí°ãÜ‰Ñ‰ÕŠyŠÏŽáã¿‘Þ‘ß—[˜¶œ³rŸ` îª‘»’¼Pô¾ÇPÇfÑbÚCÚM¸Ó" },
			new String[] { "Zhui", "×·×¹×º×¶×¸×µæíã·çÄö¿‚…´¹ˆ§‰‹ŠÜ´§é³›d®I®•³›´q´œ¸¹Š¾Y¿PÄJÄiÖÂÝÈÕ…Ù˜ÞVáWá^åFåMåYæmèVê ËíðUòKùx" },
			new String[] { "Zhun", "×¼×»ÍÍëÆñ¸ƒý†”ˆSˆÍŒd÷•H´¾œ·œÊ®líï¶›¼ƒ¾M´¿ëÓÐqÔRÕÞ„ï‚â½ün" },
			new String[] { "Zhuo",
					"×½×À×Å×Ä×¾×Æ×Ç×¿×Á½É×Â×ÃßªìÌåªÚÂä·äÃÙ¾ïíìúí½Q„†„ŸÉ×…¬†à¨‡€ˆVˆp‰~ŠƒŠß°’Á¶Þ“â“ð”½”Ù”Ú”Û”Þ•Œ—z—‡—¬èþ—Á˜‘™·šõÄ×œÊáž•žãŸO æªK¬k²ž³˜·q·‡·Ÿ¸BóçºW»S»mÀUÁMÂvëÆÉ|Þ©ËyÎ[Ï—ÐXÕ}ÕŽÖ‘Ú}õÀõÖÛ•õîãnärè@èCùhú|è¼Öø" },
			new String[] {
					"Zi",
					"×Ö×Ô×Ó×Ï×Ñ×Ê×ËÖ¨×Ò×Ð×È×É×Î×Õ×Ì×ÍóÊôÒö·ïöí§ÚÑôôç»è÷ööïÅæÜñèõþ÷ÚêßÜëö¤áÑíöæ¢ê¢ÊÂ‚•„…»…èßÚ††êŠ—ŠœŒIŒU –j–ã—Â˜h™U´Î›d›››œ¹nÐ ¼«R­uçÞ´Ã±{³Iµ›¶f¶‡·T·}¼|¾lÃcÃhÃuÆTÆ†Æ“ÆÇÈŒÉ›ËFÍIÔ`ÖJÙDÙYÚaÚƒÚÝdÝwÝ–ÔØàtâBâˆä\åOæSætÐ¿éCîoîpõ™ö‹ùƒüˆýRýUÆëýb" },
			new String[] { "Zo", "…ø†€" },
			new String[] { "Zong", "×Ü×Ý×Ú×Ø×Û×Ù××ÙÌôÕèÈëê‚~‚‚ôˆî¸¾ÙÄ¼Èß’Ö“K“i“¨•f–Q—Þ˜º|ƒœŸÐŸÙ Qª`ªf¯S¯—³Ÿ´†·O¼F¾C¾h¾t¾‘¾›¿G¿k¿v¿‚ÂCÅ‹È É~ÉÎxØqÛrÛ™åSæCçEèQòRòióWôAôiöRö`" },
			new String[] { "Zou", "×ß×á×à×ÞöíÛ¸ÚîæãÚÁ‚¸‹ƒ’ô“o×å—¯—°é¨¹t¾jÆcÇˆÕŒÚ[àYàuò|õ•öOüPýwýåÁ" },
			new String[] { "Zu", "×é×å×ã×è×â×æ×çÝÏïß×äÙÞºÇ‚y‚ú…a†XßýàÒŒþŒœáÞI–¼¾Ú´ã •«~³^·B¹Œ¼½M¾\ÜÚÈ{ÉaÔ{ÖŠôõÚŽÛnÛ€õíâžãIãJå@æcæŽæ—èì†îxñzæà" },
			new String[] { "Zuan", "×ê×ë×¬çÚõòß¬„®“S´éÔÜ”€™çºe»gÀFÀjÀyÙÜgèjè" },
			new String[] { "Zui", "×î×ì×í×ï¶Ñ¾×õþÞ©…‰†÷‡’‹¥éêŽT´Ý´é•–K–˜–è˜§˜á™d™i™Þû­r²Bµ‘·B·s½SÀxôÈÃÏ`ÞfáEáPáUäŽå@ëh" },
			new String[] { "Zun", "×ð×ñ÷®ß¤é×ƒQƒV‡g‰–µìý’Ž’Ä’Û–çžˆ¿ŸÀ–ÑI×JÛIÛZ¶×ã†ç÷VùŒú•" },
			new String[] { "Zuo", "×ö×÷×ø×ó×ù×òÔä×Á´é×ôóÐõ¡ßòìñëÑâôÚè×õÕ§‚F…øŒõŒö´ì’Û–Ã—½íÄ¶}¶š¹i¼d¿–ÆzÇgÈyÈzÉÐŠÕ‹´×â—èïŽàÜÚâ" } };

	private static LinkedHashMap SpellMap = new LinkedHashMap();
	static {
		SpellMap.put("A", new Integer(-20319));
		SpellMap.put("Ai", new Integer(-20317));
		SpellMap.put("An", new Integer(-20304));
		SpellMap.put("Ang", new Integer(-20295));
		SpellMap.put("Ao", new Integer(-20292));
		SpellMap.put("Ba", new Integer(-20283));
		SpellMap.put("Bai", new Integer(-20265));
		SpellMap.put("Ban", new Integer(-20257));
		SpellMap.put("Bang", new Integer(-20242));
		SpellMap.put("Bao", new Integer(-20230));
		SpellMap.put("Bei", new Integer(-20051));
		SpellMap.put("Ben", new Integer(-20036));
		SpellMap.put("Beng", new Integer(-20032));
		SpellMap.put("Bi", new Integer(-20026));
		SpellMap.put("Bian", new Integer(-20002));
		SpellMap.put("Biao", new Integer(-19990));
		SpellMap.put("Bie", new Integer(-19986));
		SpellMap.put("Bin", new Integer(-19982));
		SpellMap.put("Bing", new Integer(-19976));
		SpellMap.put("Bo", new Integer(-19805));
		SpellMap.put("Bu", new Integer(-19784));
		SpellMap.put("Ca", new Integer(-19775));
		SpellMap.put("Cai", new Integer(-19774));
		SpellMap.put("Can", new Integer(-19763));
		SpellMap.put("Cang", new Integer(-19756));
		SpellMap.put("Cao", new Integer(-19751));
		SpellMap.put("Ce", new Integer(-19746));
		SpellMap.put("Ceng", new Integer(-19741));
		SpellMap.put("Cha", new Integer(-19739));
		SpellMap.put("Chai", new Integer(-19728));
		SpellMap.put("Chan", new Integer(-19725));
		SpellMap.put("Chang", new Integer(-19715));
		SpellMap.put("Chao", new Integer(-19540));
		SpellMap.put("Che", new Integer(-19531));
		SpellMap.put("Chen", new Integer(-19525));
		SpellMap.put("Cheng", new Integer(-19515));
		SpellMap.put("Chi", new Integer(-19500));
		SpellMap.put("Chong", new Integer(-19484));
		SpellMap.put("Chou", new Integer(-19479));
		SpellMap.put("Chu", new Integer(-19467));
		SpellMap.put("Chuai", new Integer(-19289));
		SpellMap.put("Chuan", new Integer(-19288));
		SpellMap.put("Chuang", new Integer(-19281));
		SpellMap.put("Chui", new Integer(-19275));
		SpellMap.put("Chun", new Integer(-19270));
		SpellMap.put("Chuo", new Integer(-19263));
		SpellMap.put("Ci", new Integer(-19261));
		SpellMap.put("Cong", new Integer(-19249));
		SpellMap.put("Cou", new Integer(-19243));
		SpellMap.put("Cu", new Integer(-19242));
		SpellMap.put("Cuan", new Integer(-19238));
		SpellMap.put("Cui", new Integer(-19235));
		SpellMap.put("Cun", new Integer(-19227));
		SpellMap.put("Cuo", new Integer(-19224));
		SpellMap.put("Da", new Integer(-19218));
		SpellMap.put("Dai", new Integer(-19212));
		SpellMap.put("Dan", new Integer(-19038));
		SpellMap.put("Dang", new Integer(-19023));
		SpellMap.put("Dao", new Integer(-19018));
		SpellMap.put("De", new Integer(-19006));
		SpellMap.put("Deng", new Integer(-19003));
		SpellMap.put("Di", new Integer(-18996));
		SpellMap.put("Dian", new Integer(-18977));
		SpellMap.put("Diao", new Integer(-18961));
		SpellMap.put("Die", new Integer(-18952));
		SpellMap.put("Ding", new Integer(-18783));
		SpellMap.put("Diu", new Integer(-18774));
		SpellMap.put("Dong", new Integer(-18773));
		SpellMap.put("Dou", new Integer(-18763));
		SpellMap.put("Du", new Integer(-18756));
		SpellMap.put("Duan", new Integer(-18741));
		SpellMap.put("Dui", new Integer(-18735));
		SpellMap.put("Dun", new Integer(-18731));
		SpellMap.put("Duo", new Integer(-18722));
		SpellMap.put("E", new Integer(-18710));
		SpellMap.put("En", new Integer(-18697));
		SpellMap.put("Er", new Integer(-18696));
		SpellMap.put("Fa", new Integer(-18526));
		SpellMap.put("Fan", new Integer(-18518));
		SpellMap.put("Fang", new Integer(-18501));
		SpellMap.put("Fei", new Integer(-18490));
		SpellMap.put("Fen", new Integer(-18478));
		SpellMap.put("Feng", new Integer(-18463));
		SpellMap.put("Fo", new Integer(-18448));
		SpellMap.put("Fou", new Integer(-18447));
		SpellMap.put("Fu", new Integer(-18446));
		SpellMap.put("Ga", new Integer(-18239));
		SpellMap.put("Gai", new Integer(-18237));
		SpellMap.put("Gan", new Integer(-18231));
		SpellMap.put("Gang", new Integer(-18220));
		SpellMap.put("Gao", new Integer(-18211));
		SpellMap.put("Ge", new Integer(-18201));
		SpellMap.put("Gei", new Integer(-18184));
		SpellMap.put("Gen", new Integer(-18183));
		SpellMap.put("Geng", new Integer(-18181));
		SpellMap.put("Gong", new Integer(-18012));
		SpellMap.put("Gou", new Integer(-17997));
		SpellMap.put("Gu", new Integer(-17988));
		SpellMap.put("Gua", new Integer(-17970));
		SpellMap.put("Guai", new Integer(-17964));
		SpellMap.put("Guan", new Integer(-17961));
		SpellMap.put("Guang", new Integer(-17950));
		SpellMap.put("Gui", new Integer(-17947));
		SpellMap.put("Gun", new Integer(-17931));
		SpellMap.put("Guo", new Integer(-17928));
		SpellMap.put("Ha", new Integer(-17922));
		SpellMap.put("Hai", new Integer(-17759));
		SpellMap.put("Han", new Integer(-17752));
		SpellMap.put("Hang", new Integer(-17733));
		SpellMap.put("Hao", new Integer(-17730));
		SpellMap.put("He", new Integer(-17721));
		SpellMap.put("Hei", new Integer(-17703));
		SpellMap.put("Hen", new Integer(-17701));
		SpellMap.put("Heng", new Integer(-17697));
		SpellMap.put("Hong", new Integer(-17692));
		SpellMap.put("Hou", new Integer(-17683));
		SpellMap.put("Hu", new Integer(-17676));
		SpellMap.put("Hua", new Integer(-17496));
		SpellMap.put("Huai", new Integer(-17487));
		SpellMap.put("Huan", new Integer(-17482));
		SpellMap.put("Huang", new Integer(-17468));
		SpellMap.put("Hui", new Integer(-17454));
		SpellMap.put("Hun", new Integer(-17433));
		SpellMap.put("Huo", new Integer(-17427));
		SpellMap.put("Ji", new Integer(-17417));
		SpellMap.put("Jia", new Integer(-17202));
		SpellMap.put("Jian", new Integer(-17185));
		SpellMap.put("Jiang", new Integer(-16983));
		SpellMap.put("Jiao", new Integer(-16970));
		SpellMap.put("Jie", new Integer(-16942));
		SpellMap.put("Jin", new Integer(-16915));
		SpellMap.put("Jing", new Integer(-16733));
		SpellMap.put("Jiong", new Integer(-16708));
		SpellMap.put("Jiu", new Integer(-16706));
		SpellMap.put("Ju", new Integer(-16689));
		SpellMap.put("Juan", new Integer(-16664));
		SpellMap.put("Jue", new Integer(-16657));
		SpellMap.put("Jun", new Integer(-16647));
		SpellMap.put("Ka", new Integer(-16474));
		SpellMap.put("Kai", new Integer(-16470));
		SpellMap.put("Kan", new Integer(-16465));
		SpellMap.put("Kang", new Integer(-16459));
		SpellMap.put("Kao", new Integer(-16452));
		SpellMap.put("Ke", new Integer(-16448));
		SpellMap.put("Ken", new Integer(-16433));
		SpellMap.put("Keng", new Integer(-16429));
		SpellMap.put("Kong", new Integer(-16427));
		SpellMap.put("Kou", new Integer(-16423));
		SpellMap.put("Ku", new Integer(-16419));
		SpellMap.put("Kua", new Integer(-16412));
		SpellMap.put("Kuai", new Integer(-16407));
		SpellMap.put("Kuan", new Integer(-16403));
		SpellMap.put("Kuang", new Integer(-16401));
		SpellMap.put("Kui", new Integer(-16393));
		SpellMap.put("Kun", new Integer(-16220));
		SpellMap.put("Kuo", new Integer(-16216));
		SpellMap.put("La", new Integer(-16212));
		SpellMap.put("Lai", new Integer(-16205));
		SpellMap.put("Lan", new Integer(-16202));
		SpellMap.put("Lang", new Integer(-16187));
		SpellMap.put("Lao", new Integer(-16180));
		SpellMap.put("Le", new Integer(-16171));
		SpellMap.put("Lei", new Integer(-16169));
		SpellMap.put("Leng", new Integer(-16158));
		SpellMap.put("Li", new Integer(-16155));
		SpellMap.put("Lia", new Integer(-15959));
		SpellMap.put("Lian", new Integer(-15958));
		SpellMap.put("Liang", new Integer(-15944));
		SpellMap.put("Liao", new Integer(-15933));
		SpellMap.put("Lie", new Integer(-15920));
		SpellMap.put("Lin", new Integer(-15915));
		SpellMap.put("Ling", new Integer(-15903));
		SpellMap.put("Liu", new Integer(-15889));
		SpellMap.put("Long", new Integer(-15878));
		SpellMap.put("Lou", new Integer(-15707));
		SpellMap.put("Lu", new Integer(-15701));
		SpellMap.put("Lv", new Integer(-15681));
		SpellMap.put("Luan", new Integer(-15667));
		SpellMap.put("Lue", new Integer(-15661));
		SpellMap.put("Lun", new Integer(-15659));
		SpellMap.put("Luo", new Integer(-15652));
		SpellMap.put("Ma", new Integer(-15640));
		SpellMap.put("Mai", new Integer(-15631));
		SpellMap.put("Man", new Integer(-15625));
		SpellMap.put("Mang", new Integer(-15454));
		SpellMap.put("Mao", new Integer(-15448));
		SpellMap.put("Me", new Integer(-15436));
		SpellMap.put("Mei", new Integer(-15435));
		SpellMap.put("Men", new Integer(-15419));
		SpellMap.put("Meng", new Integer(-15416));
		SpellMap.put("Mi", new Integer(-15408));
		SpellMap.put("Mian", new Integer(-15394));
		SpellMap.put("Miao", new Integer(-15385));
		SpellMap.put("Mie", new Integer(-15377));
		SpellMap.put("Min", new Integer(-15375));
		SpellMap.put("Ming", new Integer(-15369));
		SpellMap.put("Miu", new Integer(-15363));
		SpellMap.put("Mo", new Integer(-15362));
		SpellMap.put("Mou", new Integer(-15183));
		SpellMap.put("Mu", new Integer(-15180));
		SpellMap.put("Na", new Integer(-15165));
		SpellMap.put("Nai", new Integer(-15158));
		SpellMap.put("Nan", new Integer(-15153));
		SpellMap.put("Nang", new Integer(-15150));
		SpellMap.put("Nao", new Integer(-15149));
		SpellMap.put("Ne", new Integer(-15144));
		SpellMap.put("Nei", new Integer(-15143));
		SpellMap.put("Nen", new Integer(-15141));
		SpellMap.put("Neng", new Integer(-15140));
		SpellMap.put("Ni", new Integer(-15139));
		SpellMap.put("Nian", new Integer(-15128));
		SpellMap.put("Niang", new Integer(-15121));
		SpellMap.put("Niao", new Integer(-15119));
		SpellMap.put("Nie", new Integer(-15117));
		SpellMap.put("Nin", new Integer(-15110));
		SpellMap.put("Ning", new Integer(-15109));
		SpellMap.put("Niu", new Integer(-14941));
		SpellMap.put("Nong", new Integer(-14937));
		SpellMap.put("Nu", new Integer(-14933));
		SpellMap.put("Nv", new Integer(-14930));
		SpellMap.put("Nuan", new Integer(-14929));
		SpellMap.put("Nue", new Integer(-14928));
		SpellMap.put("Nuo", new Integer(-14926));
		SpellMap.put("O", new Integer(-14922));
		SpellMap.put("Ou", new Integer(-14921));
		SpellMap.put("Pa", new Integer(-14914));
		SpellMap.put("Pai", new Integer(-14908));
		SpellMap.put("Pan", new Integer(-14902));
		SpellMap.put("Pang", new Integer(-14894));
		SpellMap.put("Pao", new Integer(-14889));
		SpellMap.put("Pei", new Integer(-14882));
		SpellMap.put("Pen", new Integer(-14873));
		SpellMap.put("Peng", new Integer(-14871));
		SpellMap.put("Pi", new Integer(-14857));
		SpellMap.put("Pian", new Integer(-14678));
		SpellMap.put("Piao", new Integer(-14674));
		SpellMap.put("Pie", new Integer(-14670));
		SpellMap.put("Pin", new Integer(-14668));
		SpellMap.put("Ping", new Integer(-14663));
		SpellMap.put("Po", new Integer(-14654));
		SpellMap.put("Pu", new Integer(-14645));
		SpellMap.put("Qi", new Integer(-14630));
		SpellMap.put("Qia", new Integer(-14594));
		SpellMap.put("Qian", new Integer(-14429));
		SpellMap.put("Qiang", new Integer(-14407));
		SpellMap.put("Qiao", new Integer(-14399));
		SpellMap.put("Qie", new Integer(-14384));
		SpellMap.put("Qin", new Integer(-14379));
		SpellMap.put("Qing", new Integer(-14368));
		SpellMap.put("Qiong", new Integer(-14355));
		SpellMap.put("Qiu", new Integer(-14353));
		SpellMap.put("Qu", new Integer(-14345));
		SpellMap.put("Quan", new Integer(-14170));
		SpellMap.put("Que", new Integer(-14159));
		SpellMap.put("Qun", new Integer(-14151));
		SpellMap.put("Ran", new Integer(-14149));
		SpellMap.put("Rang", new Integer(-14145));
		SpellMap.put("Rao", new Integer(-14140));
		SpellMap.put("Re", new Integer(-14137));
		SpellMap.put("Ren", new Integer(-14135));
		SpellMap.put("Reng", new Integer(-14125));
		SpellMap.put("Ri", new Integer(-14123));
		SpellMap.put("Rong", new Integer(-14122));
		SpellMap.put("Rou", new Integer(-14112));
		SpellMap.put("Ru", new Integer(-14109));
		SpellMap.put("Ruan", new Integer(-14099));
		SpellMap.put("Rui", new Integer(-14097));
		SpellMap.put("Run", new Integer(-14094));
		SpellMap.put("Ruo", new Integer(-14092));
		SpellMap.put("Sa", new Integer(-14090));
		SpellMap.put("Sai", new Integer(-14087));
		SpellMap.put("San", new Integer(-14083));
		SpellMap.put("Sang", new Integer(-13917));
		SpellMap.put("Sao", new Integer(-13914));
		SpellMap.put("Se", new Integer(-13910));
		SpellMap.put("Sen", new Integer(-13907));
		SpellMap.put("Seng", new Integer(-13906));
		SpellMap.put("Sha", new Integer(-13905));
		SpellMap.put("Shai", new Integer(-13896));
		SpellMap.put("Shan", new Integer(-13894));
		SpellMap.put("Shang", new Integer(-13878));
		SpellMap.put("Shao", new Integer(-13870));
		SpellMap.put("She", new Integer(-13859));
		SpellMap.put("Shen", new Integer(-13847));
		SpellMap.put("Sheng", new Integer(-13831));
		SpellMap.put("Shi", new Integer(-13658));
		SpellMap.put("Shou", new Integer(-13611));
		SpellMap.put("Shu", new Integer(-13601));
		SpellMap.put("Shua", new Integer(-13406));
		SpellMap.put("Shuai", new Integer(-13404));
		SpellMap.put("Shuan", new Integer(-13400));
		SpellMap.put("Shuang", new Integer(-13398));
		SpellMap.put("Shui", new Integer(-13395));
		SpellMap.put("Shun", new Integer(-13391));
		SpellMap.put("Shuo", new Integer(-13387));
		SpellMap.put("Si", new Integer(-13383));
		SpellMap.put("Song", new Integer(-13367));
		SpellMap.put("Sou", new Integer(-13359));
		SpellMap.put("Su", new Integer(-13356));
		SpellMap.put("Suan", new Integer(-13343));
		SpellMap.put("Sui", new Integer(-13340));
		SpellMap.put("Sun", new Integer(-13329));
		SpellMap.put("Suo", new Integer(-13326));
		SpellMap.put("Ta", new Integer(-13318));
		SpellMap.put("Tai", new Integer(-13147));
		SpellMap.put("Tan", new Integer(-13138));
		SpellMap.put("Tang", new Integer(-13120));
		SpellMap.put("Tao", new Integer(-13107));
		SpellMap.put("Te", new Integer(-13096));
		SpellMap.put("Teng", new Integer(-13095));
		SpellMap.put("Ti", new Integer(-13091));
		SpellMap.put("Tian", new Integer(-13076));
		SpellMap.put("Tiao", new Integer(-13068));
		SpellMap.put("Tie", new Integer(-13063));
		SpellMap.put("Ting", new Integer(-13060));
		SpellMap.put("Tong", new Integer(-12888));
		SpellMap.put("Tou", new Integer(-12875));
		SpellMap.put("Tu", new Integer(-12871));
		SpellMap.put("Tuan", new Integer(-12860));
		SpellMap.put("Tui", new Integer(-12858));
		SpellMap.put("Tun", new Integer(-12852));
		SpellMap.put("Tuo", new Integer(-12849));
		SpellMap.put("Wa", new Integer(-12838));
		SpellMap.put("Wai", new Integer(-12831));
		SpellMap.put("Wan", new Integer(-12829));
		SpellMap.put("Wang", new Integer(-12812));
		SpellMap.put("Wei", new Integer(-12802));
		SpellMap.put("Wen", new Integer(-12607));
		SpellMap.put("Weng", new Integer(-12597));
		SpellMap.put("Wo", new Integer(-12594));
		SpellMap.put("Wu", new Integer(-12585));
		SpellMap.put("Xi", new Integer(-12556));
		SpellMap.put("Xia", new Integer(-12359));
		SpellMap.put("Xian", new Integer(-12346));
		SpellMap.put("Xiang", new Integer(-12320));
		SpellMap.put("Xiao", new Integer(-12300));
		SpellMap.put("Xie", new Integer(-12120));
		SpellMap.put("Xin", new Integer(-12099));
		SpellMap.put("Xing", new Integer(-12089));
		SpellMap.put("Xiong", new Integer(-12074));
		SpellMap.put("Xiu", new Integer(-12067));
		SpellMap.put("Xu", new Integer(-12058));
		SpellMap.put("Xuan", new Integer(-12039));
		SpellMap.put("Xue", new Integer(-11867));
		SpellMap.put("Xun", new Integer(-11861));
		SpellMap.put("Ya", new Integer(-11847));
		SpellMap.put("Yan", new Integer(-11831));
		SpellMap.put("Yang", new Integer(-11798));
		SpellMap.put("Yao", new Integer(-11781));
		SpellMap.put("Ye", new Integer(-11604));
		SpellMap.put("Yi", new Integer(-11589));
		SpellMap.put("Yin", new Integer(-11536));
		SpellMap.put("Ying", new Integer(-11358));
		SpellMap.put("Yo", new Integer(-11340));
		SpellMap.put("Yong", new Integer(-11339));
		SpellMap.put("You", new Integer(-11324));
		SpellMap.put("Yu", new Integer(-11303));
		SpellMap.put("Yuan", new Integer(-11097));
		SpellMap.put("Yue", new Integer(-11077));
		SpellMap.put("Yun", new Integer(-11067));
		SpellMap.put("Za", new Integer(-11055));
		SpellMap.put("Zai", new Integer(-11052));
		SpellMap.put("Zan", new Integer(-11045));
		SpellMap.put("Zang", new Integer(-11041));
		SpellMap.put("Zao", new Integer(-11038));
		SpellMap.put("Ze", new Integer(-11024));
		SpellMap.put("Zei", new Integer(-11020));
		SpellMap.put("Zen", new Integer(-11019));
		SpellMap.put("Zeng", new Integer(-11018));
		SpellMap.put("Zha", new Integer(-11014));
		SpellMap.put("Zhai", new Integer(-10838));
		SpellMap.put("Zhan", new Integer(-10832));
		SpellMap.put("Zhang", new Integer(-10815));
		SpellMap.put("Zhao", new Integer(-10800));
		SpellMap.put("Zhe", new Integer(-10790));
		SpellMap.put("Zhen", new Integer(-10780));
		SpellMap.put("Zheng", new Integer(-10764));
		SpellMap.put("Zhi", new Integer(-10587));
		SpellMap.put("Zhong", new Integer(-10544));
		SpellMap.put("Zhou", new Integer(-10533));
		SpellMap.put("Zhu", new Integer(-10519));
		SpellMap.put("Zhua", new Integer(-10331));
		SpellMap.put("Zhuai", new Integer(-10329));
		SpellMap.put("Zhuan", new Integer(-10328));
		SpellMap.put("Zhuang", new Integer(-10322));
		SpellMap.put("Zhui", new Integer(-10315));
		SpellMap.put("Zhun", new Integer(-10309));
		SpellMap.put("Zhuo", new Integer(-10307));
		SpellMap.put("Zi", new Integer(-10296));
		SpellMap.put("Zong", new Integer(-10281));
		SpellMap.put("Zou", new Integer(-10274));
		SpellMap.put("Zu", new Integer(-10270));
		SpellMap.put("Zuan", new Integer(-10262));
		SpellMap.put("Zui", new Integer(-10260));
		SpellMap.put("Zun", new Integer(-10256));
		SpellMap.put("Zuo", new Integer(-10254));
	}

	/**
	 * »ñµÃµ¥¸öºº×ÖµÄAscii.
	 * 
	 * @param cn
	 *            char ºº×Ö×Ö·û
	 * @return int ´íÎó·µ»Ø 0,·ñÔò·µ»Øascii
	 */
	public static int getCnAscii(char cn) {
		byte[] bytes = (String.valueOf(cn)).getBytes();
		if (bytes == null || bytes.length > 2 || bytes.length <= 0) { // ´íÎó
			return 0;
		}
		if (bytes.length == 1) { // Ó¢ÎÄ×Ö·û
			return bytes[0];
		}
		if (bytes.length == 2) { // ÖÐÎÄ×Ö·û
			int hightByte = 256 + bytes[0];
			int lowByte = 256 + bytes[1];
			int ascii = (256 * hightByte + lowByte) - 256 * 256;
			return ascii;
		}
		return 0;
	}

	/**
	 * ¸ù¾ÝASCIIÂëµ½SpellMapÖÐ²éÕÒ¶ÔÓ¦µÄÆ´Òô
	 * 
	 * @param ascii
	 *            int ×Ö·û¶ÔÓ¦µÄASCII
	 * @return String Æ´Òô,Ê×ÏÈÅÐ¶ÏASCIIÊÇ·ñ>0&<160,Èç¹ûÊÇ·µ»Ø¶ÔÓ¦µÄ×Ö·û, <BR>
	 *         ·ñÔòµ½SpellMapÖÐ²éÕÒ,Èç¹ûÃ»ÓÐÕÒµ½Æ´Òô,Ôò·µ»Ønull,Èç¹ûÕÒµ½Ôò·µ»ØÆ´Òô.
	 */
	private static String getSpellByAscii(int ascii) {
		if (ascii > 0 && ascii < 160) { // µ¥×Ö·û
			return String.valueOf((char) ascii);
		}
		if (ascii < -20319 || ascii > -10247) { // ²»ÖªµÀµÄ×Ö·û
			return null;
		}
		Set keySet = SpellMap.keySet();
		Iterator it = keySet.iterator();

		String spell0 = null;
		String spell = null;

		int asciiRang0 = -20319;
		int asciiRang;
		while (it.hasNext()) {
			spell = (String) it.next();
			Object valObj = SpellMap.get(spell);
			if (valObj instanceof Integer) {
				asciiRang = ((Integer) valObj).intValue();

				if (ascii >= asciiRang0 && ascii < asciiRang) { // Çø¼äÕÒµ½
					return (spell0 == null) ? spell : spell0;
				} else {
					spell0 = spell;
					asciiRang0 = asciiRang;
				}
			}
		}
		return null;
	}

	/**
	 * ·µ»Ø×Ö·û´®µÄÈ«Æ´,ÊÇºº×Ö×ª»¯ÎªÈ«Æ´,ÆäËü×Ö·û²»½øÐÐ×ª»»
	 * 
	 * @param str
	 *            String ×Ö·û´®
	 * @return String ×ª»»³ÉÈ«Æ´ºóµÄ×Ö·û´®
	 */
	public static String convert(String str) {
		if (str == null) {
			return null;
		}
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0, Len = chars.length; i < Len; i++) {
			int ascii = getCnAscii(chars[i]);
			if (ascii == 0) { // È¡asciiÊ±³ö´í
				sb.append(chars[i]);
			} else {
				String spell = getSpellByAscii(ascii);
				if (spell == null) {
					sb.append(convert2(chars[i]));
				} else {
					sb.append(spell);
				}
			}
		}
		return sb.toString();
	}

	private static String convert2(char c) {
		for (int j = 0; j < HZALL.length; j++) {
			if (HZALL[j][1].indexOf(c) != -1) {
				return HZALL[j][0];
			}
		}
		return String.valueOf(c);
	}

	/**
	 * µÃµ½ÐÕÃûµÚÒ»¸öºº×ÖµÄÈ«Æ´
	 */
	public static String convertName(String str) {
		if (str == null) {
			return null;
		}
		String first = str.substring(0, 1);
		String v = (String) HZXS.get(first);
		if (v != null) {
			return v;
		}
		return convert(first);
	}

	private static final char chartable[] = { '\u554A', '\u82AD', '\u64E6', '\u642D', '\u86FE', '\u53D1', '\u5676',
			'\u54C8', '\u54C8', '\u51FB', '\u5580', '\u5783', '\u5988', '\u62FF', '\u54E6', '\u556A', '\u671F',
			'\u7136', '\u6492', '\u584C', '\u584C', '\u584C', '\u6316', '\u6614', '\u538B', '\u531D', '\u5EA7' };

	private static final char alphatable[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static int[] table = new int[27];
	static {
		for (int i = 0; i < 27; ++i) {
			table[i] = gbValue(chartable[i]);
		}
	}

	/**
	 * ÊäÈë×Ö·û,µÃµ½ËûµÄÉùÄ¸, Ó¢ÎÄ×ÖÄ¸·µ»Ø¶ÔÓ¦µÄ´óÐ´×ÖÄ¸ Êý×ÖÖ±½Ó·µ»Ø ÆäËû·Ç¼òÌåºº×Ö·µ»Ø '0'
	 * 
	 * @param ch
	 * @return
	 */
	public static char Char2Alpha(char ch) {

		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');
		if (ch >= 'A' && ch <= 'Z')
			return ch;
		if (ch >= '0' && ch <= '9') {
			return ch;
		}
		int gb = gbValue(ch);
		if (gb < table[0])
			return '0';

		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb))
				break;
		}

		if (i >= 26)
			return '0';
		else
			return alphatable[i];
	}

	/**
	 * ¸ù¾ÝÒ»¸ö°üº¬ºº×ÖµÄ×Ö·û´®·µ»ØÒ»¸öºº×ÖÆ´ÒôÊ××ÖÄ¸µÄ×Ö·û´®
	 * 
	 * @param SourceStr
	 * @return
	 */
	public static String getFirstAlpha(String SourceStr) {
		char[] cs = SourceStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cs.length; i++) {
			char c = cs[i];
			if (SourceStr.substring(i, i + 1).matches("[\\w]\\-")) {
				sb.append(c);
			} else {
				String str = convert2(c);
				str = str.replaceAll("\\s", "");
				if (str.length() > 0) {
					sb.append(str.substring(0, 1));
				}
			}
		}

		return sb.toString();
	}

	private static boolean match(int i, int gb) {
		if (gb < table[i])
			return false;

		int j = i + 1;

		// ×ÖÄ¸ZÊ¹ÓÃÁËÁ½¸ö±êÇ©
		while (j < 26 && (table[j] == table[i]))
			++j;

		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];

	}

	/**
	 * È¡³öºº×ÖµÄ±àÂë
	 * 
	 * @param ch
	 * @return
	 */
	private static int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GB2312");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}

	}
}
