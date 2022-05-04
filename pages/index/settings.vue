<template>
	<view>
		<u-toast ref="uToast"></u-toast>
		
		<u-row customStyle="margin-bottom: 10px" justify="center">
			<u--image class="user-icon" :showLoading="true" shape="circle" width="200rpx" height="200rpx" :src="userIcon"></u--image>
		</u-row>
		<u-row customStyle="margin-bottom: 10px" justify="center">
			<view class="user-name">{{ displayName }}</view>
			<u-icon :name="user.gender ? 'man' : 'woman'" :color="user.gender ? '#2979ff' : '#ff5577'"></u-icon>
		</u-row>
		<u-divider customStyle="margin-bottom: 0;"></u-divider>
		<u-row @click="gotoProfile">
			<u-col span="11">
				<u-row>
					<u--image :showLoading="true" shape="circle" width="120rpx" height="120rpx" src="../../static/icon/account.png"></u--image>
					<text>Account</text>
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
					<u--image :showLoading="true" shape="circle" width="120rpx" height="120rpx" src="../../static/icon/wallet.png"></u--image>
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
					<u--image :showLoading="true" shape="circle" width="120rpx" height="120rpx" src="../../static/icon/security.png"></u--image>
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
					<u--image :showLoading="true" shape="circle" width="120rpx" height="120rpx" src="../../static/icon/logout.png"></u--image>
					<text>Logout</text>
				</u-row>
			</u-col>
			<u-col span="1">
				<u-icon name="arrow-right"></u-icon>
			</u-col>
		</u-row>
		<u-divider class="divisor"></u-divider>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userIcon: "http://127.0.0.1:8080/image/usericon/Ellipse.png",
				displayName: "Adline Castelino",
				
				user: { }
			}
		},
		
		onLoad() {
			
		},
		
		onShow(params) {
			// Obtain user's data
			uni.$u.http.post('self').then(
				res => {
					this.user = res.data;
					this.displayName = this.user.displayName
				}
			)
		},
		
		methods: {
			gotoProfile() {
				uni.$u.route({
					url: "pages/sub/profile",
					type: "to",
					params: {
						name: this.displayName,
						weight: this.user.weight,
						height: this.user.height,
						gender: this.user.gender
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
				
				this.$refs.uToast.show({
					message: "Successfully logout!",
					complete() {
						uni.$u.route({
							url: "pages/index/index",
							type: "reLaunch"
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
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
		font-size: 30rpx;
		color: #555;
	}
</style>
