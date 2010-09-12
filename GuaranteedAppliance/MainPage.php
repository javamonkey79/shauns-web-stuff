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
	Header!
	</div>

	<a class="sideTab" href="#" onclick="toggleContentDiv('homeDiv');">Home</a>
	<a class="sideTab" href="#" onclick="toggleContentDiv('hoursAndLocationDiv');">Hours & Location</a>
	<a class="sideTab" href="#" onclick="toggleContentDiv('onlineInventoryDiv');">Online Inventory</a>
	<a class="sideTab" href="#" onclick="toggleContentDiv('salesContentDiv');">Sales</a>
	<a class="sideTab" href="#" onclick="toggleContentDiv('repairsDiv');">Service & Repairs</a>
	<a class="sideTab" href="#" onclick="toggleContentDiv('faqDiv');">FAQ</a>

	<div class="outerContent">
			<div class="innerContent" id="homeDiv" style="visibility: visible;">
			<div style="text-align: center; background-color: blue; display: table; width: 90%; border: black solid 1px; margin-left: 5%; margin-top: 10px;">
				<img alt="Our Store Front" src="images/home-image.jpg" />
			</div>
			<p>
				Welcome to Guaranteed Applicance Spokane! At Guaranteed Applicance Spokane we believe in honesty, quality
				and fair prices. We don't like to sell junk! blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah  		
			</p>
			<p>
				blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah 
			</p>
		</div>
		<div class="innerContent" id="hoursAndLocationDiv" >
			<div style="white-space:pre; border-bottom: solid 1px black; width: 100%;">
<span class="emphasis smallHeader">STORE HOURS</span>

<span class="emphasis">Monday-Friday:</span> 9am-5pm
<span class="emphasis">Saturday:</span> 10am-1pm [optional: not open EVERY Saturday, please call]
<span class="emphasis">Sunday:</span> Closed 
			</div>
			<div style="white-space:pre; margin-top: 10px;">
<span class="emphasis smallHeader">LOCATION</span>

<div id="map_canvas" style="width: 625px; height: 500px; border: solid 1px black; margin-left: auto; margin-right: auto;"></div>
			</div>
		</div>
		<div class="innerContent" id="onlineInventoryDiv">
			<table style="width: 100%; height: 75%; text-align: center">
				<tr>
					<td>
						<img src="images/6.jpg" alt='<?php print $text6; ?>' />
					</td>
					<td>
						<img src="images/7.jpg" alt='<?php print $text7; ?>' />
					</td>
				</tr>
				<tr>
					<td>
						<?php print $text6; ?>
					</td>
					<td>
						<?php print $text7; ?>
					</td>
				</tr>
				<tr>
					<td>
						<img src="images/8.jpg" alt='<?php print $text8; ?>' />
					</td>
					<td>
						<img src="images/9.jpg" alt='<?php print $text9; ?>' />
					</td>
				</tr>
				<tr>
					<td>
						<?php print $text8; ?>
					</td>
					<td>
						<?php print $text9; ?>
					</td>
				</tr>
				<tr>
					<td>
						<img src="images/10.jpg" alt='<?php print $text10; ?>' />
					</td>
					<td>
						<img src="images/11.jpg" alt='<?php print $text11; ?>' />
					</td>
				</tr>
				<tr>
					<td>
						<?php print $text10; ?>
					</td>
					<td>
						<?php print $text11; ?>
					</td>
				</tr>
				<tr>
					<td>
						<img src="images/12.jpg" alt='<?php print $text12; ?>' />
					</td>
					<td>
						<img src="images/13.jpg" alt='<?php print $text13; ?>' />
					</td>
				</tr>
				<tr>
					<td>
						<?php print $text12; ?>
					</td>
					<td>
						<?php print $text13; ?>
					</td>
				</tr>
				<tr>
					<td>
						<img src="images/14.jpg" alt='<?php print $text14; ?>' />
					</td>
					<td>
						<img src="images/15.jpg" alt='<?php print $text15; ?>' />
					</td>
				</tr>
				<tr>
					<td>
						<?php print $text14; ?>
					</td>
					<td>
						<?php print $text15; ?>
					</td>
				</tr>
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
			</ul>
			<div style="white-space:pre; width: 100%;">
<span class="emphasis smallerHeader">NEW</span>

Additionally, we also sell the following new appliances, by discounted catalog order of brand Crosley*:
<span style="font-style: italic; font-size: 8pt;">*orders take 3 to 4 days to receive appliance</span>
			</div>
			<ul>
				<li>New Washers</li>
				<li>New Dryers</li>
				<li>New Refrigerators</li>
				<li>New Ovens</li>
			</ul> 
		</div>
		<div class="innerContent" id="repairsDiv">
			<div style="white-space:pre; width: 100%;">
<span class="emphasis smallHeader">SERVICE & REPAIRS</span>
			</div>
			
			<p>We offer services on the following appliances for the low rate of <span class="emphasis">$45 for the first hour and $X each additional, plus parts</span>. Bring it to us, or we can come to you, either way we can get it done!</p>
			<p style="text-indent: 0px;">Our expert, certified E.P.A. technician with over 25 years of experience can do the following:</p>
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
			<p class="answer"><span class="qaLabel">A:</span> When you buy appliances this way you get what you pay for. Sometimes you might get a good deal, but more often than not you're buying someone else's problem. Our appliances are quality refurbished machines; cleaned, inspected and rebuilt with quality parts.</p>
			   
			<p class="question"><span class="qaLabel">Q:</span> Why should I buy from you and not one of the other local used appliance shops?</p>
			<p class="answer"><span class="qaLabel">A:</span> We're passionate about quality. We don't always have $100 appliances for a good reason - they tend to be beat up and fall apart. We provide a written guarantee with every sell, but most of the time it is never needed!</p>
			   
			<p class="question"><span class="qaLabel">Q:</span> Why should I buy new from you?</p>
			<p class="answer"><span class="qaLabel">A:</span> Simple. Big box stores and larger local retailers have higher costs and higher overhead. We don't have big showrooms or large fleets, instead we pass the savings off to you.</p> 
		
			<p class="question"><span class="qaLabel">Q:</span> How come you don't offer all brands of new appliances?</p>
			<p class="answer"><span class="qaLabel">A:</span> There are certain brands we don't want to sell - we know how they're made and can not in good conscience sell them due to thier inferior quality.</p>		
		</div>
	</div>
		<?php print getCounterImages(count(file("log.txt"))); ?>
</div>

</body>
<script type="text/javascript">
    $(function() { $('.sideTab').corner(); } );
</script>
<?php
    function getCounterImages($numVisitors){
	    $numVisitorsStr = (string)$numVisitors;
	    $array = preg_split('//', $numVisitorsStr, -1, PREG_SPLIT_NO_EMPTY);
	    $retStr = "<"."div class=\"counter\">\n";

	    foreach($array as $char){
		    $retStr .= "\t<img alt=\"$char\" src=\"./images/counterimages/$char.jpg\" />\n";
	    }
	    $retStr .= "</div\n";
	
	    return $retStr;
    }
    
    function checkIP(){
        $ip = getenv("REMOTE_ADDR");
        $lines = file("log.txt");
        foreach($lines as $line){
            if(strpos($line, $ip) !== false){
                //print "not adding";
                return;
            }
        }
        writeIP($ip);
    }

    function writeIP($ip){ 
        //print "adding...";
        
        $log = "\n$ip"; 
        $fp = fopen("log.txt", "a"); 
        fwrite($fp, $log);
        fclose($fp);
    }
    
    checkIP();
?>
</html>