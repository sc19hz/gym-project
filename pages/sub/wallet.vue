<template>
	<view style="width: 80vw; margin-left: 10vw;">
		<view style="font-size: 50rpx; font-weight: bold; margin-top: 20rpx;">Wallet</view>
		
		<view style="background-color: #5ba6f7; color: #fff; border-radius: 30rpx; padding: 20rpx; margin-top: 30rpx;">
			<u-row>
				<u-col>
					<view>My Balance</view>
					<view style="font-size: 50rpx;">{{ formatBalance(user.amount) }}£</view>
				</u-col>
			</u-row>
		</view>
		
		<view @click="gotoTopupRecords" style="background-color: #fc5; color: #fff; border-radius: 30rpx; padding: 20rpx; margin-top: 30rpx;">
			<u-row>
				<u-col span="11">
					<view style="">View top-up records</view>
				</u-col>
				<u-col span="1">
					<u-icon name="arrow-right" color="#fff"></u-icon>
				</u-col>
			</u-row>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: { }
			}
		},
		
		async onLoad() {
			this.user = await uni.$u.http.post("/self");
		},
		
		methods: {
			formatBalance(value) {
				let v = value + '';
				return !v.match(/\.[0-9]+/) ? v + '.0' : v;
			},
			
			gotoTopupRecords() {
				uni.$u.route({
					url: "pages/sub/topup-records",
					type: "to"
				});
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
