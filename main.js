import App from './App'

// #ifndef VUE3
import Vue from 'vue'

// main.js
import uView from '@/uni_modules/uview-ui'
Vue.use(uView)

// 调用setConfig方法，方法内部会进行对象属性深度合并，可以放心嵌套配置
// 需要在Vue.use(uView)之后执行
uni.$u.setConfig({
	// 修改$u.config对象的属性
	config: {
		// 修改默认单位为rpx，相当于执行 uni.$u.config.unit = 'rpx'
		unit: 'rpx',
	},
	// 修改$u.props对象的属性
	props: {
		// 修改radio组件的size参数的默认值，相当于执行 uni.$u.props.radio.size = 30
		// radio: {
		// 	size: 15
		// }
		// 其他组件属性配置
		// ......
	}
})

// 请求拦截器
uni.$u.http.interceptors.request.use(config => { // 可使用async await 做异步操作
	console.log("get request params");
	console.log(config.params);
	config.header = {
		...config.header,
		Authorization: 'Bearer ' + uni.getStorageSync("token") // 演示拦截器header加参
	}
	// 演示custom 用处
	// if (config.custom.auth) {
	//   config.header.token = 'token'
	// }
	// if (config.custom.loading) {
	//  uni.showLoading()
	// }
	// 演示
	// if (!token) { // 如果token不存在，return Promise.reject(config) 会取消本次请求
	// 	return Promise.reject(config)
	// }
	return config
})

// 响应拦截器
uni.$u.http.interceptors.response.use(
	response => {
		/* 对响应成功做点什么 可使用async await 做异步操作*/
		// if (response.data.code !== 200) { // 服务端返回的状态码不等于200，则reject()
		//    	return Promise.reject(response) // return Promise.reject 可使promise状态进入catch
		// if (response.config.custom.verification) { // 演示自定义参数的作用
		//   	return response.data
		// }
		
		// Pre-check status code
		// if(response.statusCode >= 400)
		// 	return { 
		// 		status: response.statusCode,
		// 		message: response.message
		// 	};
		
		// Switch status code
		let {data} = response
		switch(data.status)
		{
		case 401:
			// Token has expired, redirect to login page
			uni.$u.route({
				url: "pages/auth/login",
				type: "redirect",
				params: {
					tokenExpire: data.message
				}
			})
			break
		default:
			return data
		}
		
		// Reject the promise if handled in 'case'
		return Promise.reject(response)
	}
)

uni.$u.http.setConfig((config) => {
    /* config 为默认全局配置*/
    config.baseURL = 'http://127.0.0.1:8080'; /* 根域名 */
    return config
})

Vue.config.productionTip = false
App.mpType = 'app'
const app = new Vue({
    ...App
})
app.$mount()
// #endif

// #ifdef VUE3
import { createSSRApp } from 'vue'
export function createApp() {
  const app = createSSRApp(App)
  return {
    app
  }
}
// #endif