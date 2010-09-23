<?php
    include("./specials text.php");
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="main.css" type="text/css" />
<script type="text/javascript" src="javascript/jquery.js"></script>
<script type="text/javascript" src="javascript/jquery.corner.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" src="javascript/guaranteedappliance.js"></script>
<title>Guaranteed Appliance Spokane</title>
</head>
<body onload="initializeMap()">
<div class="main">
	<div class="header">
		<img src="images/common/header.jpg" alt="Guaranteed">
		<span class="phoneNumbers">
~MAIN: (509) 928-3368~	~CELL: (509) 218-4683~
		</span>
	</div>

	<a class="sideTab rounded" href="#" onclick="toggleContentDiv('homeDiv');">Home</a>
	<a class="sideTab rounded" href="#" onclick="toggleContentDiv('hoursAndLocationDiv');">Hours & Location</a>
	<a class="sideTab rounded" href="#" onclick="toggleContentDiv('onlineInventoryDiv');">Online Inventory</a>
	<a class="sideTab rounded" href="#" onclick="toggleContentDiv('salesContentDiv');">Sales</a>
	<a class="sideTab rounded" href="#" onclick="toggleContentDiv('repairsDiv');">Service & Repairs</a>
	<a class="sideTab rounded" href="#" onclick="toggleContentDiv('faqDiv');">FAQ</a>
	<img class="side" alt="credit accepted" src="images/common/visamasteramex.gif" />
	<a class="side" href="http://www.cashforapplianceswa.com" target="_blank"><img style="border: none;" src="images/common/CashforAppliances.jpg" alt="Cash for Appliances - Washington" /></a>

	<div class="outerContent">
			<div class="innerContent" id="homeDiv" style="visibility: visible;">
				<div class="homeImageWrapper">
					<img class="border" style="" alt="Our Store Front" src="images/common/home-image.jpg" />
				</div>
			<p>
				Welcome to Guaranteed Appliance Sales and Service! At Guaranteed Applicance we believe in honesty, quality
				and fair prices. We are a family owned and operated business that specializes in appliance sales and service.
				It is our mission to provide you with superior products and services in a hassle free, friendly environment.
			</p>
			<p>
				We know that you work hard for what you have, so we don't want you to waste your hard earned dollars on something that will
				"just get you through" or something that will fall apart as soon as you get it home. We don't like to sell junk! 
				We sell new and used appliances at great prices. We also do service calls as well as in-shop repairs. 
				Delivery is available upon request as well as removal of your old appliance. 
			</p>
			<p>				
				We will never ask you to bring in a competitor's business card or come in on special days to
				receive a discount. Our goal is to help you with your appliance needs without jumping through
				unnecessary hoops. Feel free to check out our <span class="emphasis">"A"</span> rating on the <a target="_blank" href="http://www.bbb.org/eastern-washington/business-reviews/appliances-major-dealers/guaranteed-appliance-sales-service-in-spokane-valley-wa-27000348/">Better Business Bureau</a> website.
			</p>
			<p>				
				You can take confidence in our expert technician. He has over 30 years of experience, E.P.A. refrigeration certification as well as Appliance Repair Certification.
				In addition, all sales come with a written warranty - this is the "guarantee" in Guaranteed Appliance. 
			</p>
			<p>
				<span style="font-weight: bold;">Come in and let us help you find what your looking for. We look forward to serving you!</span>
			</p>
		</div>
		<div class="innerContent" id="hoursAndLocationDiv" >
			<div style="white-space:pre; border-bottom: solid 1px black; width: 100%;">
<span class="emphasis smallHeader">STORE HOURS</span>

<span class="emphasis">Monday-Friday:</span> 9am-5pm
<span class="emphasis">Saturday:</span> 10am-3pm [optional: not open EVERY Saturday, please call]
<span class="emphasis">Sunday:</span> Closed 
			</div>
			<div style="white-space:pre; margin-top: 10px;">
<span class="emphasis smallHeader">LOCATION</span>

<div style="text-align: center; font-weight: bold;">7509 E. TRENT SPOKANE VALLEY, WA. 99212</div>
<div id="map_canvas" style="width: 625px; height: 500px; border: solid 1px black; margin-left: auto; margin-right: auto;"></div>
			</div>
		</div>
		<div class="innerContent" id="onlineInventoryDiv">
			<table style="width: 100%; height: 75%; text-align: center">
			<?php 
				$multiRowFormat = "
				<tr>
					<td>
						<img class=\"border\" src=\"images/inventory/%s.jpg\" alt='%s' />
					</td>
					<td>
						<img class=\"border\" src=\"images/inventory/%s.jpg\" alt='%s' />
					</td>
				</tr>
				<tr>
					<td>
						%s
					</td>
					<td>
						%s
					</td>
				</tr>
				";

				$textArray = array(6 => $text6,7 => $text7,8 => $text8,9 => $text9,10 => $text10,11 => $text11,12 => $text12,13 => $text13,14 => $text14,15 => $text15);
				
				for ($iRows = 6; $iRows < 16; $iRows += 2 ) {
					printf($multiRowFormat, $iRows, $textArray[$iRows],$iRows + 1, $textArray[$iRows + 1], $textArray[$iRows], $textArray[$iRows + 1]);
				}
			?>
			</table>		
		</div>
		<div class="innerContent" id="salesContentDiv">
			<div style="white-space:pre; width: 100%;">
<span class="emphasis smallHeader">SALES</span>

<span class="emphasis smallerHeader">USED</span>

We sell the following quality pre-owned appliances: 
			</div>
			<ul>
				<li>Used Washers</li>
				<li>Used Dryers</li>
				<li>Used Refrigerators</li>
				<li>Used Ovens</li>
				<li>Used Microwaves (not always in stock, please call)</li>
				<li>Used Appliance Parts</li>
			</ul>
			<div style="white-space:pre; width: 100%;">
<span class="emphasis smallerHeader">NEW</span>

We sell the following quality Crosley appliances, by discounted catalog order*:
<span style="font-style: italic; font-size: 8pt;">*orders may take 3 to 4 days to receive appliance</span>
			</div>
			<ul>
				<li>New Washers</li>
				<li>New Dryers</li>
				<li>New Refrigerators</li>
				<li>New Ovens</li>
				<li>New Appliance Parts</li>
			</ul> 
		</div>
		<div class="innerContent" id="repairsDiv">
			<div style="white-space:pre; width: 100%;">
<span class="emphasis smallHeader">SERVICE & REPAIRS</span>
			</div>
			
			<p>We offer appliance service and repair for the low rate of <span class="emphasis">$40 per hour plus parts.</span> Service calls are $45 for the first hour and in shop estimates are $20. Bring it to us, or we can come to you, either way we can get it done!</p>
			<p style="text-indent: 0px;">We can do the following:</p>
			<ul>
				<li>Washer Repairs</li>
				<li>Dryer Repairs</li>
				<li>Refrigerator Repairs</li>
				<li>Oven Repairs</li>
				<li>Microwave Repairs</li>
			</ul>
		</div>
		<div class="innerContent" id="faqDiv">
			<div style="white-space:pre; width: 100%;">
<span class="emphasis smallHeader">FAQ</span>
			</div>
			
			<p class="question"><span class="qaLabel">Q:</span> Why should I buy from you and not someone on Craigslist, in a newspaper, garage sale, etc.?</p>
			<p class="answer"><span class="qaLabel">A:</span> When you buy appliances this way you get what you pay for. Sometimes you will get a good deal, but more often than not you're buying someone else's problem. Our appliances are quality refurbished machines; cleaned, inspected and rebuilt with quality parts.</p>
			   
			<p class="question"><span class="qaLabel">Q:</span> Why should I buy from you and not one of the other local used appliance shops?</p>
			<p class="answer"><span class="qaLabel">A:</span> We're passionate about quality. We don't always have $100 appliances for a good reason - they tend to be beat up and fall apart. We provide a written guarantee with every sell, but most of the time it is never needed!</p>
			   
			<p class="question"><span class="qaLabel">Q:</span> Why should I buy new from you?</p>
			<p class="answer"><span class="qaLabel">A:</span> Simple. Big box stores and larger local retailers have higher costs and higher overhead. We don't have big showrooms or large fleets, instead we pass the savings off to you.</p> 
		
			<p class="question"><span class="qaLabel">Q:</span> How come you don't offer all brands of new appliances?</p>
			<p class="answer"><span class="qaLabel">A:</span> There are 3 main reasons: 1) There are certain brands we don't want to sell - we know how they're made and can not in good conscience sell them due to thier inferior quality. 3) Crosley appliances come with a <span class="emphasis">10 year warranty.</span> 2) Most other brands don't offer sales in small quantities - they only want big box customers, not small retailers.</p>
			
			<p class="question"><span class="qaLabel">Q:</span> Do you accept old\unwanted appliances?</p>
			<p class="answer"><span class="qaLabel">A:</span> We are always looking for good used appliances that are working or not.</p>
					
			<p class="question"><span class="qaLabel">Q:</span> Can I drop off my old\unwanted appliances?</p>
			<p class="answer"><span class="qaLabel">A:</span> Please call for approval.</p>			
			
			<p class="question"><span class="qaLabel">Q:</span> Will you pick up my old\unwanted appliances?</p>
			<p class="answer"><span class="qaLabel">A:</span> We will gladly do curb-side or drive-way pick up in the Spokane area on approved appliances.</p>			
		</div>
	</div>
		<?php print getCounterImages(count(file("log.txt"))); ?>
</div>

</body>
<script type="text/javascript">
    $(function() { $('.rounded').corner(); } );
</script>
<?php
    function getCounterImages($numVisitors){
	    $numVisitorsStr = (string)$numVisitors;
	    $array = preg_split('//', $numVisitorsStr, -1, PREG_SPLIT_NO_EMPTY);
	    $retStr = "<"."div class=\"counter\">\n";

	    foreach($array as $char){
		    $retStr .= "\t\t\t<img class=\"counterImages\" alt=\"$char\" src=\"./images/counterimages/$char.jpg\" />\n";
	    }
	    $retStr .= "\t\t</div>\n";
	
	    return $retStr;
    }
    
    function checkIP(){
        $ip = getenv("REMOTE_ADDR");
        $lines = file("log.txt");
        foreach($lines as $line){
            if(strpos($line, $ip) !== false){
                return;
            }
        }
        writeIP($ip);
    }

    function writeIP($ip){ 
        $log = "\n$ip"; 
        $fp = fopen("log.txt", "a"); 
        fwrite($fp, $log);
        fclose($fp);
    }
    
    checkIP();
?>
</html>