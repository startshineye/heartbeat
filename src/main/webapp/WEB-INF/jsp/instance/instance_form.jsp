<%--
 * 
 * @author Shengzhao Li
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>${formDto.newly?'添加':'编辑'} 实例</title>
</head>
<body>
<script>
$().ready(function() {
	
	 $("#instanceType").val("URL");
	  $("#monitorUrl").attr("required","true").attr("maxlength","225");
	  
	  $("#sqlurl").removeAttr("required").removeAttr("maxlength");
	  $("#username").removeAttr("required").removeAttr("maxlength");
	  $("#password").removeAttr("required").removeAttr("maxlength");
	  $("#testsql").removeAttr("required").removeAttr("maxlength");
	
	$('.nav-tabs a').click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		  if($(this).attr("aria-controls")=='taburl'){
			  $("#instanceType").val("URL");
			  $("#monitorUrl").attr("required","true").attr("maxlength","225");
			  
			  $("#sqlurl").removeAttr("required").removeAttr("maxlength");
			  $("#username").removeAttr("required").removeAttr("maxlength");
			  $("#password").removeAttr("required").removeAttr("maxlength");
			  $("#testsql").removeAttr("required").removeAttr("maxlength");
		  }
		  else if($(this).attr("aria-controls")=='tabdatabase'){
			  $("#instanceType").val("DATABASE");
 			  $("#sqlurl").attr("required","true").attr("maxlength","225");
 			  $("#username").attr("required","true").attr("maxlength","225");
 			  $("#password").attr("required","true").attr("maxlength","225");
 			  $("#testsql").attr("required","true").attr("maxlength","225");
			  
			  $("#monitorUrl").removeAttr("required").removeAttr("maxlength");
		  }
		  
		}) 
});
</script>
<div>
    <div class="row">
        <div class="col-md-12">
            <h4>${formDto.newly?'添加':'编辑'} 实例</h4>
            <form:form commandName="formDto" cssClass="form-horizontal">
                <form:hidden path="instanceType" id="instanceType"  />
               
                <div>
				   <ul class="nav nav-tabs" role="tablist">
				    <li role="presentation" class="active"><a href="#taburl" aria-controls="taburl" role="tab" data-toggle="tab">URL</a></li>
				    <li role="presentation"><a href="#tabdatabase" aria-controls="tabdatabase" role="tab" data-toggle="tab">DATABASE</a></li>
				    <li role="presentation"><a href="#tabftp" aria-controls="tabftp" role="tab" data-toggle="tab">FTP</a></li>
				  </ul>
				
			     <div class="tab-content" style="padding-top: 50px">
					<div role="tabpanel" class="tab-pane" id="tabdatabase">
						<div class="form-group">
							<label for=databaseType class="col-sm-2 control-label">数据库类型</label>
							<div class="col-sm-8">
								<form:select path="databaseType" id="databaseType"
									cssClass="form-control">
									<form:options items="${formDto.databaseTypes}"
										itemLabel="name" itemValue="name" />
								</form:select>
								<p class="help-block"></p>
								<form:errors path="frequency" cssClass="text-danger" />
							</div>
						</div>

						<div class="form-group">
							<label for="sqlurl" class="col-sm-2 control-label">数据库连接字符串</label>
							<div class="col-sm-8">
								<form:input path="sqlurl" id="sqlurl"
									cssClass="form-control " placeholder="" 
									 />
								<p class="help-block">例如：jdbc:mysql://120.24.41.213:3306/heart_beat?</p>
								<form:errors path="databaseurl" cssClass="text-danger" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">数据库用户名</label>
							<div class="col-sm-8">
								<form:input path="username" id="username"
									cssClass="form-control" placeholder="root" maxlength="255"
									required="true" />
								<p class="help-block">例如：mysql:root</p>
								<form:errors path="username" cssClass="text-danger" />
							</div>
						</div>

						<div class="form-group">
							<label for="password" class="col-sm-2 control-label">数据库密码</label>
							<div class="col-sm-8">
								<form:input path="password" id="password"
									cssClass="form-control" placeholder="" maxlength="255"
									required="true" />
								<p class="help-block"></p>
								<form:errors path="password" cssClass="text-danger" />
							</div>
						</div>

						<div class="form-group">
							<label for="testsql" class="col-sm-2 control-label">测试sql</label>
							<div class="col-sm-8">
								<form:input path="testsql" id="testsql"
									cssClass="form-control" placeholder="select 1 from dual"
									maxlength="255" required="true" />
								<p class="help-block">用来测试数据库是否正常的sql语句</p>
								<form:errors path="testsql" cssClass="text-danger" />
							</div>
						</div>
					</div>
					<!-- end datablase panel -->

							<div role="tabpanel" class="tab-pane active" id="taburl">
								<div class="form-group">
									<label for="monitorUrl" class="col-sm-2 control-label">监控URL</label>

									<div class="col-sm-8">
										<form:input path="monitorUrl" id="monitorUrl"
											cssClass="form-control" placeholder="http://..."
											maxlength="255" required="true" />
										<p class="help-block">监控URL以 'http' 或 'https' 开头, 如:
											'http://andaily.com/test.html'</p>
										<form:errors path="monitorUrl" cssClass="text-danger" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">请求方式</label>

									<div class="col-sm-8">
										<label class="toggle-radio"> <input type="radio"
											name="requestMethod" value="GET"
											${formDto.requestMethod.get?'checked':''} /> GET
										</label> &nbsp; <label class="toggle-radio"> <input
											type="radio" name="requestMethod" value="POST"
											${formDto.requestMethod.post?'checked':''} /> POST
										</label>

										<p class="help-block">指定监控URL的请求方式. 默认: GET</p>
										<form:errors path="requestMethod" cssClass="text-danger" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">请求参数</label>

									<div class="col-sm-8">
										<table class="table table-responsive table-condensed">
											<thead>
												<tr>
													<td>参数-Key</td>
													<td>参数-Value</td>
													<th>&nbsp;</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${formDto.urlParameters}" var="p"
													varStatus="s">
													<tr order="${s.index}">
														<td class="col-xs-3"><form:input
																path="urlParameters[${s.index}].key"
																cssClass="form-control input-sm" maxlength="20"
																placeholder="请求参数 Key" /></td>
														<td class="col-xs-6">
															<div class="input-group">
																<input type="text"
																	name="urlParameters[${s.index}].value"
																	value="${p.value}" class="form-control input-sm value"
																	${p.randomValue?'readonly':''} maxlength="255"
																	placeholder="请求参数 Value" /> <span
																	class="input-group-addon input-sm"> <input
																	type="checkbox" value="true" class="random"
																	name="urlParameters[${s.index}].randomValue"
																	${p.randomValue?'checked':''} /> 随机值
																</span>
															</div>
														</td>
														<td><c:set var="hiddenStyle"
																value="${(s.last and formDto.urlParametersSize > 0)?'':'hidden'}" />
															<a href="javascript:void(0);"
															class="addParam ${hiddenStyle}" title="添加"><em
																class="fui-plus-circle"></em></a> <a
															href="javascript:void(0);"
															class="deleteParam ${hiddenStyle}" title="删除"><em
																class="fui-cross-circle"></em></a></td>
													</tr>
												</c:forEach>
												<c:if test="${empty formDto.urlParameters}">
													<tr order="0">
														<td class="col-xs-3"><form:input
																path="urlParameters[0].key"
																cssClass="form-control input-sm" maxlength="20"
																placeholder="参数 Key" /></td>
														<td class="col-xs-6">
															<div class="input-group">
																<form:input path="urlParameters[0].value"
																	maxlength="255" cssClass="form-control input-sm value"
																	placeholder="参数 Value" />
																<span class="input-group-addon input-sm"> <input
																	type="checkbox" value="true"
																	name="urlParameters[0].randomValue" class="random" />
																	随机值
																</span>
															</div>
														</td>
														<td><a href="javascript:void(0);" class="addParam"
															title="添加"><em class="fui-plus-circle"></em></a> <a
															href="javascript:void(0);" class="deleteParam hidden"
															title="删除"><em class="fui-cross-circle"></em></a></td>
													</tr>
												</c:if>
											</tbody>
										</table>

										<p class="help-block">指定监控URL时的请求参数, 允许设置参数值为随机数. (可选)</p>
										<form:errors path="urlParameters" cssClass="text-danger" />
									</div>
								</div>

								<div class="form-group">
									<label for="contentType" class="col-sm-2 control-label">ContentType</label>

									<div class="col-sm-8">
										<form:select path="contentType" id="contentType"
											cssClass="form-control">
											<form:option value="">无</form:option>
											<form:options items="${formDto.contentTypes}"
												itemLabel="mimeType" itemValue="mimeType" />
										</form:select>
										<p class="help-block">指定请求时的 'contentType' 如果监控URL需要, (可选)</p>
										<form:errors path="contentType" cssClass="text-danger" />
									</div>
								</div>
							</div>
							
							<!-- end taburl -->
							
						</div>
			
			</div>
			                
                
                <div class="form-group">
                    <label for="instanceName" class="col-sm-2 control-label">实例名称</label>

                    <div class="col-sm-8">
                        <form:input path="instanceName" id="instanceName" cssClass="form-control"
                                    placeholder="请输入实例名称" required="true" maxlength="255"/>
                        <p class="help-block">对该监控的称呼, 名称必须唯一</p>
                        <form:errors path="instanceName" cssClass="text-danger"/>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="frequency" class="col-sm-2 control-label">频率</label>
                    <div class="col-sm-8">
                        <form:select path="frequency" id="frequency" cssClass="form-control">
                            <form:options items="${formDto.frequencies}" itemLabel="seconds" itemValue="value"/>
                        </form:select>
                        <p class="help-block">再次监控请求之间的间隔时间, 单位:秒</p>
                        <form:errors path="frequency" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">连续失败次数</label>

                    <div class="col-sm-8">
                        <form:select path="continueFailedTimes" cssClass="form-control">
                            <c:forEach begin="1" end="5" var="step">
                                <form:option value="${step}" label="${step}"/>
                            </c:forEach>
                        </form:select>
                        <p class="help-block">该参数用于设定在连续几次连接失败后才发送提醒, 默认为2;
                            一般在检测状态不正常时, 有可能是因为网络故障(如常见的:丢包,dns故障,闪断)而出现问题, 这时一发现不正常就发送提醒的意义不大</p>
                        <form:errors path="continueFailedTimes" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="maxConnectionSeconds" class="col-sm-2 control-label"><abbr
                            title='最大连接时间(s)'>最大连接时间</abbr></label>

                    <div class="col-sm-8">
                        <form:input path="maxConnectionSeconds" id="maxConnectionSeconds" cssClass="form-control"
                                    placeholder="请输入最大连接时间(s)" required="true"/>
                        <p class="help-block">每次发送请求时的最大连接时间, 默认为频率时间, 单位:秒</p>
                        <form:errors path="maxConnectionSeconds" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email" class="col-sm-2 control-label">接收提醒邮箱</label>

                    <div class="col-sm-8">
                        <form:input path="email" id="email" cssClass="form-control"
                                    placeholder="请输入邮箱" required="true"/>
                        <p class="help-block">当心跳监控不正常时,HB将向添加的邮箱发送邮件提醒; 多个邮箱地址用分号(;)分隔</p>
                        <form:errors path="email" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">私有实例</label>

                    <div class="col-sm-8">
                        <form:checkbox path="privateInstance"/>
                        <p class="help-block">若该实例只允许自己登录后查看,请勾选上</p>
                        <form:errors path="privateInstance" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">微信号</label>

                    <div class="col-sm-8">
                        <ul class="list-group">
                            <c:if test="${empty formDto.weixinUserDtos}">
                                <li class="list-group-item">
                                    <div class="row">
                                        <div class="col-md-8">
                                            <p class="text-muted">请先用微信扫描二维码, 关注与绑定HB账号, 若需要微信推送提醒 (可选). </p>
                                        </div>
                                        <div class="col-md-4">
                                            <a href="${contextPath}/resources/images/iegreen-wx.jpg"
                                               target="_blank"><img
                                                    src="${contextPath}/resources/images/iegreen-wx.jpg"
                                                    class="img-rounded" style="max-height: 120px;"/></a>
                                        </div>
                                    </div>
                                </li>
                            </c:if>
                            <c:forEach items="${formDto.weixinUserDtos}" var="weiUser">
                                <li class="list-group-item">
                                    <form:checkbox path="weixinUserGuids" value="${weiUser.guid}"/> ${weiUser.nickName}
                                    (${weiUser.openId})
                                </li>
                            </c:forEach>
                        </ul>
                        <p class="help-block">当心跳监控不正常时, HB将向绑定的微信号通过第三方工具及时推送提醒消息(可选)</p>
                        <form:errors path="weixinUserGuids" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="remark" class="col-sm-2 control-label">备注</label>

                    <div class="col-sm-8">
                        <form:textarea path="remark" id="remark" rows="3"
                                       cssClass="form-control"
                                       placeholder="请输入关于该实例的备注信息"/>
                        <p class="help-block">关于该实例的备注信息; (可选)</p>
                        <form:errors path="remark" cssClass="text-danger"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-primary"><em class="fui-check-circle"></em> 保存</button>
                        &nbsp;<a href="list.hb">取消</a>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
   
<script>
    $(function () {
        new InstanceForm();
    });
</script>
</body>
</html>