<template>
	<view>
		<u-row>
			<u-col span="6">
				<image
					src="../../static/icon/theme.png"
					mode="aspectFit"
					style="width: 50%; height: 100vh; margin-left: 20%;"
				></image>
			</u-col>
			
			<u-col span="6" customStyle="padding-right: 20%;">
				<view style="font-weight: bold; font-size: 46rpx; margin-bottom: 80rpx;">Regis with code</view>
				<u--form>
					<view style="color: #2d3748;">Email</view>
					<u-form-item>
						<u--input customStyle="border-radius: 10rpx;" v-model="email"></u--input>
					</u-form-item>
					
					<view style="color: #2d3748; margin-top: 40rpx;">Password</view>
					<u-form-item>
						<u--input customStyle="border-radius: 10rpx;" type="password" v-model="password"></u--input>
					</u-form-item>
					
					<view style="color: #2d3748; margin-top: 40rpx;">Invitation code</view>
					<u-form-item>
						<u--input customStyle="border-radius: 10rpx;" v-model="invitationCode"></u--input>
					</u-form-item>
					
					<u-row customStyle="margin-top: 10rpx;">
						<u-checkbox-group v-model="protocolSelect">
							<u-checkbox size="36rpx" name="ok"></u-checkbox>
						</u-checkbox-group>
						<view style="color: #2d3748;">
							I agree to all the<text style="margin: 0 10rpx; color: #007aff;">Terms</text>and<text style="margin: 0 10rpx; color: #007aff;">Privacy policy</text>
						</view>
					</u-row>
					
					<u-alert v-if="errMsg!=''" :closable="true" title="Error" type = "error" :description="errMsg" fontSize="33rpx" :showIcon="true" customStyle="margin-top: 30rpx;"></u-alert>
					
					<u-row customStyle="margin-top: 100rpx;">
						<u-button color="#007aff" @click="tryRegis" :disabled="!email||!password||!invitationCode||protocolSelect.length==0" text="Register" customStyle="border-radius: 10rpx;"></u-button>
					</u-row>
					
					<view @click="gotoLogin" style="text-align: center; color: #2d3748; margin-top: 40rpx;">
						Ready have account?<text style="color: #007aff; margin: 0 10rpx;">Click to login!</text>
					</view>
				</u--form>
			</u-col>
		</u-row>
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
			invitationCode: "",
			protocolSelect: [ ],
			
			errMsg: "",
		};
	},
	
	onLoad(params) {
		this.errMsg = params.errMsg ?? ''
	},
	
	methods: {
		checkForm() {
			this.flag = !this.email || !this.password || !this.invitationCode;
		},
		
		async tryRegis() {
			let msg = 'Unexpected error';
			switch(1)
			{
			default:
				if(!this.$u.test.email(this.email))
				{
					msg = "Invalid email format, please check and retry!";
					break;
				}
				
				let data = await uni.$u.http.post(
					'/login', { }, { params: { email: this.email, password: this.password } },
				);
				
				if(data.status != 200)
				{
					msg = data.message;
					break;
				}
				
				// Save token to local storage
				uni.setStorageSync("token", data.token)
				
				// Check if has manager permission
				let res = await uni.$u.http.post("/check-invitation", { }, { params: { invitationCode: this.invitationCode } });
				if(res.status != 200)
				{
					uni.removeStorageSync("token");
					msg = res.message;
					break;
				}
				
				// Jump to home page
				uni.$u.route({
					url: "pages/sub/manage",
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
			});
		},
		
		gotoLogin() {
			uni.$u.route({
				url: "pages/auth/login",
				type: "reLaunch"
			})
		}
	},
};
</script>

<style lang="scss" scoped>
	
</style>