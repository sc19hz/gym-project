<template>
	<view>
		<u-row customStyle="margin-bottom: 10px" justify="center">
			<u--image class="user-icon" :showLoading="true" shape="circle" width="200rpx" height="200rpx" :src="userIcon"></u--image>
		</u-row>
		<u-row customStyle="margin-bottom: 10px" justify="center">
			<view class="user-name">{{ displayName }}</view>
			<u-icon :name="user.gender ? 'man' : 'woman'" :color="user.gender ? '#2979ff' : '#ff5577'" size="34rpx"></u-icon>
		</u-row>
		<u-divider customStyle="margin-bottom: 0;"></u-divider>
		
		<u-row @click="gotoReservations">
			<u-col span="11">
				<u-row>
					<view class="icon-container">
						<image class="icon" src="../../static/icon/record.png"></image>
					</view>
					<text>Reservations</text>
				</u-row>
			</u-col>
			<u-col span="1">
				<u-icon name="arrow-right"></u-icon>
			</u-col>
		</u-row>
		<u-divider class="divisor"></u-divider>
		
		<u-row @click="gotoProfile">
			<u-col span="11">
				<u-row>
					<view class="icon-container">
						<image class="icon" src="../../static/icon/profile.png"></image>
					</view>
					<text>Profile</text>
				</u-row>
			</u-col>
			<u-col span="1">
				<u-icon name="arrow-right"></u-icon>
			</u-col>
		</u-row>
		<u-divider class="divisor"></u-divider>
		
		<u-row @click="gotoWallet">
			<u-col span="11">
				<u-row>
					<view class="icon-container">
						<image class="icon" src="../../static/icon/wallet.png"></image>
					</view>
					<text>Wallet</text>
				</u-row>
			</u-col>
			<u-col span="1">
				<u-icon name="arrow-right"></u-icon>
			</u-col>
		</u-row>
		<u-divider class="divisor"></u-divider>
		
		<u-row>
			<u-col span="11">
				<u-row>
					<view class="icon-container">
						<image class="icon" src="../../static/icon/security.png"></image>
					</view>
					<text>Security</text>
				</u-row>
			</u-col>
			<u-col span="1">
				<u-icon name="arrow-right"></u-icon>
			</u-col>
		</u-row>
		<u-divider class="divisor"></u-divider>
		
		<u-row @click="logout">
			<u-col span="11">
				<u-row>
					<view class="icon-container">
						<image class="icon" src="../../static/icon/logout.png"></image>
					</view>
					<text>Logout</text>
				</u-row>
			</u-col>
			<u-col span="1">
				<u-icon name="arrow-right"></u-icon>
			</u-col>
		</u-row>
		<u-divider class="divisor"></u-divider>
		
		<view style="height: 120rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userIcon: "",
				displayName: "???",
				
				user: { }
			}
		},
		
		onLoad() {
			
		},
		
		async onShow(params) {
			// Obtain user's data
			let data = await uni.$u.http.post('/self');
			this.user = data;
			this.displayName = data.displayName;
			this.userIcon = data.icon;
		},
		
		methods: {
			gotoReservations() {
				uni.$u.route({
					url: "pages/sub/reservation",
					type: "to"
				})
			},
			
			gotoProfile() {
				uni.$u.route({
					url: "pages/sub/profile",
					type: "to",
					params: {
						name: this.displayName,
						weight: this.user.weight,
						height: this.user.height,
						gender: this.user.gender,
						icon: this.userIcon
					}
				})
			},
			
			gotoWallet() {
				uni.$u.route({
					url: "pages/sub/wallet",
					type: "to"
				})
			},
			
			logout() {
				uni.removeStorageSync("token");
				
				uni.showToast({
					title: "Successfully logout!",
					duration: 1000
				})
				
				setTimeout(
					() => uni.$u.route({
						url: "pages/index/index",
						type: "reLaunch",
					}),
					1000
				);
			}
		}
	}
</script>

<style lang="scss" scoped>
	.icon-container {
		height: 120rpx;
		width: 120rpx;
		
		.icon {
			height: 50rpx;
			width: 50rpx;
			margin-top: 35rpx;
			margin-left: 35rpx;
		}
	}
	
	.divisor {
		margin: 0;
	}
	
	.user-icon {
		padding-top: 3vh;
		margin: 0 auto;
		display: block;
	}
	
	.user-name {
		font-weight: bold;
		font-size: 34rpx;
		color: #777;
	}
</style>
