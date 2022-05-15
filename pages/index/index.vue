<template>
	<view>
		<image style="position: absolute; right: 0; top: 0; width: 70%; height: 40%;" src="../../static/index/top-right.png"></image>
		<image style="position: absolute; bottom: 0; left: 0; width: 100%; height: 70%;" src="../../static/index/bottom-left.png"></image>
		<image style="position: absolute; top: 28vh; left: 10vw; width: 85vw; height: 40vw;" src="../../static/index/promote.png"></image>
		<view style="position: absolute; left: 0; top: 0; width: 100vw;">
			<u-row justify="space-between" gutter="10" customStyle="padding-top: 75vh">
			    <u-col span="4" offset="1">
			        <u-button class="login" text="Login" @click="gotoLogin"></u-button>
			    </u-col>
			    <u-col span="4" offset="2">
			        <u-button class="regis" text="Sign up" @click="gotoRegis"></u-button>
			    </u-col>
				<u-col span="1"></u-col>
			</u-row>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		
		onLoad() {
			// console.log("hello");
		},
		
		methods: {
			gotoLogin() {
				uni.$u.route({
					url: 'pages/auth/login',
					type: 'to'
				});
			},
			
			gotoRegis() {
				uni.$u.route({
					url: 'pages/auth/regis',
					type: 'to',
				});
			}
		},
	}
</script>

<style lang="scss" scoped>
	.login {
		font-weight: bold;
		border-radius: 20rpx;
		border-color: #000;
	}
	
	.regis {
		font-weight: bold;
		border-radius: 20rpx;
		border-color: #000;
		background-color: #000;
		color: #fff;
	}
	
	.bg-img-top-right {
		position: flex;
		right: 0;
		z-index: -2;
	}
</style>
