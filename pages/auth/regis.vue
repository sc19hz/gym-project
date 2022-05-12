<template>
	<view style="width: 80vw; margin-left: 10vw;">
		<view class="title">Sign Up</view>
		<u--form>
			<u-form-item class="input-field">
				<u--input @change="checkForm" border="bottom" placeholder="Email" v-model="email"></u--input>
			</u-form-item>
			<u-form-item class="input-field">
				<u--input @change="checkForm" type="password" border="bottom" placeholder="Password" v-model="password"></u--input>
			</u-form-item>
			<u-form-item class="input-field">
				<u--input @change="checkForm" type="password" border="bottom" placeholder="Re-enter Password" v-model="confirmPassword"></u--input>
			</u-form-item>
			<u-form-item class="protocol">
				<u-checkbox-group @change="agree = !agree;checkForm();" size="30rpx" labelSize="30rpx">
					<u-checkbox
						:customStyle="{marginBottom: '8px'}"
						label="I agree with the user protocol"
					></u-checkbox>
				</u-checkbox-group>
			</u-form-item>
			
			<u-alert v-if="errMsg != ''" :closable="true" title="Error" type = "error" :description="errMsg" fontSize="30rpx" :showIcon="true"></u-alert>
			
			<u-form-item class="regis-button">
				<u-button class="button" @click="onRegis()" :disabled="flag" text="Register!" size="large"></u-button>
			</u-form-item>
			
			<u-form-item>
				<view class="protocol-link" @click="gotoUserProtocol()">Check out user protocol</view>
			</u-form-item>
		</u--form>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// Set to false if conditions for register have been matched
				flag: true,
				
				// Whether agreed with protocol or not
				agree: false,
				
				displayName: "",
				email: "",
				password: "",
				confirmPassword: "",
				
				errMsg: ""
			}
		},
		
		onLoad(params) {
			this.errMsg = params.errMsg ?? ''
		},
		
		methods: {
			checkForm() {
				this.flag = !this.email || !this.password || !this.confirmPassword || !this.agree
			},
			
			async onRegis() {
				let msg = 'Unexpected error'
				switch(true)
				{
				default:
					if(!this.$u.test.email(this.email))
					{
						msg = "Invalid email format, please check and retry!";
						break;
					}
					
					if(this.password != this.confirmPassword)
					{
						msg = "Wrong confirmed password, please check and retry!";
						break;
					}
					
					// Primary check pass, do register request
					let data = await uni.$u.http.post(
						'/regis', { }, { params: { displayName: this.displayName, email: this.email, password: this.password } }
					);
					
					if(data.status != 200)
					{
						msg = data.message;
						break;
					}
					
					console.log(data);
					
					data = await uni.$u.http.post(
						'/login', { }, { params: { email: this.email, password: this.password } },
					);
					
					console.log(data);
					
					// Save token to local storage
					uni.setStorageSync("token", data.token)
			
					// Jump to home page
					uni.$u.route({
						url: "pages/index/home",
						type: "reLaunch"
					})
					return;
				}
				
				// Something goes wrong, redirect to this page and show error message
				uni.$u.route({
					url: "pages/auth/regis",
					type: "redirect",
					params: {
						errMsg: msg
					}
				})
			},
			
			gotoUserProtocol() {
				uni.$u.route('pages/auth/user-protocol')
			}
		}
	}
</script>

<style lang="scss" scoped>
	.protocol-link {
		font-size: 20rpx;
		margin: 0 auto;
		color: $uni-color-primary;
		text-decoration: underline;
	}
	
	.protocol {
		text-align: center;
	}
	
	.title {
		font-size: 60rpx;
		font-weight: bold;
		padding-top: 10vh;
	}
	
	.input-field {
		padding-top: 2vh;
	}
	
	.regis-button {
		padding-top: 10vh;
		
		.button {
			font-weight: bold;
			border-radius: 20rpx;
			border-color: #000;
			background-color: #000;
			color: #fff;
		}
	}
</style>
