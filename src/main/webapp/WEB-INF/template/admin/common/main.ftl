[#assign shiro=JspTaglibs["/WEB-INF/tld/shiro.tld"] /]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.main.title")} </title>
<meta name="author" content="Sunry" />
<meta name="copyright" content="" />
<link href="${base}/resources/admin/css/common.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/admin/css/main.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/resources/admin/js/jquery.js"></script>
<style type="text/css">
*{
	font: 12px tahoma, Arial, Verdana, sans-serif;
}
html, body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
<script type="text/javascript">
$().ready(function() {

	var $nav = $("#nav a:not(:last)");
	var $menu = $("#menu dl");
	var $menuItem = $("#menu a");
	
	$nav.click(function() {
		var $this = $(this);
		$nav.removeClass("current");
		$this.addClass("current");
		var $currentMenu = $($this.attr("href"));
		$menu.hide();
		$currentMenu.show();
		return false;
	});
	
	$menuItem.click(function() {
		var $this = $(this);
		$menuItem.removeClass("current");
		$this.addClass("current");
	});

});
</script>
</head>
<body>
	<script type="text/javascript">
		if (self != top) {
			top.location = self.location;
		};
	</script>
	<table class="main">
		<tr>
			<th class="logo">
				<a href="main.jhtml">
					<img src="${base}/resources/admin/images/1.png" width="90px" height="40px" alt="后台管理" />
				</a>
			</th>
			<th>
				<div id="nav" class="nav">
					<ul>
						[#list ["admin:product", "admin:productCategory", "admin:shop", "admin:virtualShopCategory", "admin:appAdvertise", "shopadmin:product", "shopadmin:shopCategory", "shopadmin:parameterGroup", "shopadmin:attribute", "shopadmin:specification", "shopadmin:shopsetting", "shopadmin:activity"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#product">${message("admin.main.productNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["admin:order", "admin:payment", "admin:refunds", "admin:shipping", "admin:returns", "admin:deliveryCenter", "admin:deliveryTemplate"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#order">${message("admin.main.orderNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["admin:member", "admin:memberRank", "admin:memberAttribute", "admin:review", "admin:consultation"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#member">${message("admin.main.memberNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["admin:navigation", "admin:article", "admin:articleCategory", "admin:tag", "admin:friendLink", "admin:template", "admin:cache", "admin:static", "admin:index"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#content">${message("admin.main.contentNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["admin:promotion", "admin:coupon", "admin:seo", "admin:sitemap","shopadmin:promotion"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#marketing">${message("admin.main.marketingNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["admin:statistics", "admin:sales", "admin:salesRanking", "admin:purchaseRanking", "admin:deposit"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#statistics">${message("admin.main.statisticsNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						[#list ["admin:setting", "admin:area", "admin:paymentMethod", "admin:shippingMethod", "admin:deliveryCorp", "admin:paymentPlugin", "admin:storagePlugin", "admin:admin", "admin:role", "admin:message", "admin:log"] as permission]
							[@shiro.hasPermission name = permission]
								<li>
									<a href="#system">${message("admin.main.systemNav")}</a>
								</li>
								[#break /]
							[/@shiro.hasPermission]
						[/#list]
						<li>
							<a href="${base}/" target="_blank">${message("admin.main.home")}</a>
						</li>
					</ul>
				</div>
				<div class="link">
					<a href="#" target="_blank">${message("admin.main.about")}</a>
				</div>
				<div class="link">
					<strong>[@shiro.principal /]</strong>
					${message("admin.main.hello")}!
					<a href="../profile/edit.jhtml" target="iframe">[${message("admin.main.profile")}]</a>
					<a href="../logout.jsp" target="_top">[${message("admin.main.logout")}]</a>
				</div>
			</th>
		</tr>
		<tr>
			<td id="menu" class="menu">
				<dl id="product" class="default">
					<dt>${message("admin.main.productGroup")}</dt>
					[@shiro.hasPermission name="admin:product"]
						<dd>
							<a href="../product/list.jhtml" target="iframe">${message("admin.main.product")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:productCategory"]
						<dd>
							<a href="../product_category/list.jhtml" target="iframe">${message("admin.main.productCategory")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:shop"]
						<dd>
							<a href="../shop/list.jhtml" target="iframe">${message("admin.role.shop")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:virtualShopCategory"]
						<dd>
							<a href="../virtual_shop_category/list.jhtml" target="iframe">${message("admin.role.virtualShopCategory")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:appAdvertise"]
						<dd>
							<a href="../app_ad/list.jhtml" target="iframe">${message("admin.role.appAdvertise")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:shopsetting"]
						<dd>
							<a href="/shopadmin/shop/edit.jhtml" target="iframe">${message("shopadmin.main.shopsetting")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:product"]
						<dd>
							<a href="/shopadmin/product/list.jhtml" target="iframe">${message("shopadmin.main.product")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:shopCategory"]
						<dd>
							<a href="/shopadmin/shop_category/list.jhtml" target="iframe">${message("shopadmin.role.shopCategory")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:parameterGroup"]
						<dd>
							<a href="/shopadmin/parameter_group/list.jhtml" target="iframe">${message("admin.main.parameterGroup")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:attribute"]
						<dd>
							<a href="/shopadmin/attribute/list.jhtml" target="iframe">${message("admin.main.attribute")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:specification"]
						<dd>
							<a href="/shopadmin/specification/list.jhtml" target="iframe">${message("admin.main.specification")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:activity"]
						<dd>
							<a href="/shopadmin/activity/list.jhtml" target="iframe">${message("shopadmin.main.activity")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
				<dl id="order">
					<dt>${message("admin.main.orderGroup")}</dt>
					[@shiro.hasPermission name="admin:order"]
						<dd>
							<a href="../order/list.jhtml" target="iframe">${message("admin.main.order")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:payment"]
						<dd>
							<a href="../payment/list.jhtml" target="iframe">${message("admin.main.payment")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:refunds"]
						<dd>
							<a href="../refunds/list.jhtml" target="iframe">${message("admin.main.refunds")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:shipping"]
						<dd>
							<a href="../shipping/list.jhtml" target="iframe">${message("admin.main.shipping")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:returns"]
						<dd>
							<a href="../returns/list.jhtml" target="iframe">${message("admin.main.returns")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:deliveryCenter"]
						<dd>
							<a href="../delivery_center/list.jhtml" target="iframe">${message("admin.main.deliveryCenter")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:deliveryTemplate"]
						<dd>
							<a href="../delivery_template/list.jhtml" target="iframe">${message("admin.main.deliveryTemplate")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
				<dl id="member">
					<dt>${message("admin.main.memberGroup")}</dt>
					[@shiro.hasPermission name="admin:member"]
						<dd>
							<a href="../member/list.jhtml" target="iframe">${message("admin.main.member")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:memberRank"]
						<dd>
							<a href="../member_rank/list.jhtml" target="iframe">${message("admin.main.memberRank")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:memberAttribute"]
						<dd>
							<a href="../member_attribute/list.jhtml" target="iframe">${message("admin.main.memberAttribute")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:review"]
						<dd>
							<a href="../review/list.jhtml" target="iframe">${message("admin.main.review")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:consultation"]
						<dd>
							<a href="../consultation/list.jhtml" target="iframe">${message("admin.main.consultation")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
				<dl id="content">
					<dt>${message("admin.main.contentGroup")}</dt>
					[@shiro.hasPermission name="admin:navigation"]
						<dd>
							<a href="../navigation/list.jhtml" target="iframe">${message("admin.main.navigation")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:article"]
						<dd>
							<a href="../article/list.jhtml" target="iframe">${message("admin.main.article")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:articleCategory"]
						<dd>
							<a href="../article_category/list.jhtml" target="iframe">${message("admin.main.articleCategory")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:tag"]
						<dd>
							<a href="../tag/list.jhtml" target="iframe">${message("admin.main.tag")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:friendLink"]
						<dd>
							<a href="../friend_link/list.jhtml" target="iframe">${message("admin.main.friendLink")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:template"]
						<dd>
							<a href="../template/list.jhtml" target="iframe">${message("admin.main.template")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:cache"]
						<dd>
							<a href="../cache/clear.jhtml" target="iframe">${message("admin.main.cache")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:static"]
						<dd>
							<a href="../static/build.jhtml" target="iframe">${message("admin.main.static")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:index"]
						<dd>
							<a href="../index/build.jhtml" target="iframe">${message("admin.main.index")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
				<dl id="marketing">
					<dt>${message("admin.main.marketingGroup")}</dt>
					[@shiro.hasPermission name="admin:promotion"]
						<dd>
							<a href="../promotion/list.jhtml" target="iframe">${message("admin.main.promotion")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:coupon"]
						<dd>
							<a href="../coupon/list.jhtml" target="iframe">${message("admin.main.coupon")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:seo"]
						<dd>
							<a href="../seo/list.jhtml" target="iframe">${message("admin.main.seo")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:sitemap"]
						<dd>
							<a href="../sitemap/build.jhtml" target="iframe">${message("admin.main.sitemap")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="shopadmin:promotion"]
						<dd>
							<a href="/shopadmin/promotion/list.jhtml" target="iframe">${message("admin.main.promotion")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
				<dl id="statistics">
					<dt>${message("admin.main.statisticsGroup")}</dt>
					[@shiro.hasPermission name="admin:statistics"]
						<dd>
							<a href="../statistics/view.jhtml" target="iframe">${message("admin.main.statistics")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:statistics"]
						<dd>
							<a href="../statistics/setting.jhtml" target="iframe">${message("admin.main.statisticsSetting")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:sales"]
						<dd>
							<a href="../sales/view.jhtml" target="iframe">${message("admin.main.sales")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:salesRanking"]
						<dd>
							<a href="../sales_ranking/list.jhtml" target="iframe">${message("admin.main.salesRanking")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:purchaseRanking"]
						<dd>
							<a href="../purchase_ranking/list.jhtml" target="iframe">${message("admin.main.purchaseRanking")}</a>
						</dd>
					[/@shiro.hasPermission]
						[@shiro.hasPermission name="admin:deposit"]
						<dd>
							<a href="../deposit/list.jhtml" target="iframe">${message("admin.main.deposit")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
				<dl id="system">
					<dt>${message("admin.main.systemGroup")}</dt>
					[@shiro.hasPermission name="admin:setting"]
						<dd>
							<a href="../setting/edit.jhtml" target="iframe">${message("admin.main.setting")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:area"]
						<dd>
							<a href="../area/list.jhtml" target="iframe">${message("admin.main.area")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:paymentMethod"]
						<dd>
							<a href="../payment_method/list.jhtml" target="iframe">${message("admin.main.paymentMethod")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:shippingMethod"]
						<dd>
							<a href="../shipping_method/list.jhtml" target="iframe">${message("admin.main.shippingMethod")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:deliveryCorp"]
						<dd>
							<a href="../delivery_corp/list.jhtml" target="iframe">${message("admin.main.deliveryCorp")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:paymentPlugin"]
						<dd>
							<a href="../payment_plugin/list.jhtml" target="iframe">${message("admin.main.paymentPlugin")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:storagePlugin"]
						<dd>
							<a href="../storage_plugin/list.jhtml" target="iframe">${message("admin.main.storagePlugin")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:admin"]
						<dd>
							<a href="../admin/list.jhtml" target="iframe">${message("admin.main.admin")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:role"]
						<dd>
							<a href="../role/list.jhtml" target="iframe">${message("admin.main.role")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:message"]
						<dd>
							<a href="../message/send.jhtml" target="iframe">${message("admin.main.send")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:message"]
						<dd>
							<a href="../message/list.jhtml" target="iframe">${message("admin.main.message")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:message"]
						<dd>
							<a href="../message/draft.jhtml" target="iframe">${message("admin.main.draft")}</a>
						</dd>
					[/@shiro.hasPermission]
					[@shiro.hasPermission name="admin:log"]
						<dd>
							<a href="../log/list.jhtml" target="iframe">${message("admin.main.log")}</a>
						</dd>
					[/@shiro.hasPermission]
				</dl>
			</td>
			<td>
				<iframe id="iframe" name="iframe" src="index.jhtml" frameborder="0"></iframe>
			</td>
		</tr>
	</table>
</body>
</html>