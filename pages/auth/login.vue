<template>
	<view style="width: 80vw; padding-left: 10vw;">
		<view class="title">Login</view>
		<u--form>
			<u-form-item class="input-field">
				<u--input @change="checkForm" border="bottom" placeholder="Email" v-model="email"></u--input>
			</u-form-item>
			<u-form-item class="input-field">
				<u--input @change="checkForm" type="password" border="bottom" placeholder="Password" v-model="password"></u--input>
			</u-form-item>
			
			<u-alert v-if="errMsg != ''" :closable="true" title="Error" type = "error" :description="errMsg" fontSize="30rpx" :showIcon="true"></u-alert>
			
			<u-form-item class="login-button">
				<u-button class="button" @click="onLogin()" :disabled="flag" text="Login" size="large"></u-button>
			</u-form-item>
			<u-form-item class="regis-button">
				<u-button class="button" @click="onRegis()" text="Sign up" size="large"></u-button>
			</u-form-item>
			
			<u-form-item>
				<view class="promote-msg">Don't have ID yet? Click here!</view>
			</u-form-item>
		</u--form>
	</view>
</template>

<script>
export default {
	data() {
		return {
			// Set to false if entered email and password
			flag: true,
			
			email: "",
			password: "",
			
			errMsg: "",
		};
	},
	
	onLoad(params) {
		this.errMsg = params.errMsg ?? ''
	},
	
	methods: {
		checkForm() {
			this.flag = !this.email || !this.password
		},
		
		onLogin() {
			let msg = 'Unexpected error';
			if(!this.$u.test.email(this.email))
				msg = "Invalid email format, please check and retry!"
			else
			{
				// console.log("try login with email <" + this.email + ">, password <" + this.password + ">")
				
				uni.$u.http.post(
					'/login', { }, { params: { email: this.email, password: this.password } },
				).then(
					res => {
						let {data} = res
						console.log(data);
					
						// Save token to local storage
						uni.setStorageSync("token", data.token)
						console.log("token saved");
				
						// Jump to home page
						uni.$u.route({
							url: "pages/index/home",
							type: "reLaunch"
						})
					}
				)
			}
			
			// Something goes wrong, redirect to this page and show error message
			uni.$u.route({
				url: "pages/auth/login",
				type: "redirect",
				params: {
					errMsg: msg
				}
			})
		},
		
		onRegis() {
			uni.$u.route({
				url: "pages/auth/regis",
				type: "redirect"
			})
		}
	},
};
</script>

<style lang="scss" scoped>
	.err-msg {
		// font-weight: bold;
		font-size: 20rpx;
		color: #f66;
	}
	
	.title {
		font-size: 60rpx;
		font-weight: bold;
		padding-top: 10vh;
	}
	
	.input-field {
		padding-top: 2vh;
	}
	
	.button {
		font-weight: bold;
	}
	
	.login-button {
		padding-top: 10vh;
		
		.button {
			border-radius: 20rpx;
			border-color: #000;
			background-color: #000;
			color: #fff;
		}
	}
	
	.regis-button {
		padding-top: 1vh;
		
		.button {
			border-radius: 20rpx;
		}
	}
	
	.promote-msg {
		margin: 0 auto;
		color: #aaa;
	}
</style>