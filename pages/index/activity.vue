<template>
	<view style="width: 80vw; margin-left: 10vw;">
		<u-row>
			<image style="height: 120rpx; width: 120rpx; margin: 40rpx 30rpx 0 0;" mode="aspectFit" :src="user.icon"></image>
			<view style="margin-top: 60rpx;">
				<view style="font-size: 40rpx; font-weight: bold;">{{ user.displayName }}</view>
				<view style="color: #808080;">{{ user.email }}</view>
			</view>
		</u-row>
		<u-row style="margin-top: 30rpx;" justify="center">
			<u-tabs lineColor="black" :list="monthes"></u-tabs>
		</u-row>
		<view style="font-size: 36rpx; font-weight: bold; margin-top: 20rpx;">Sports record</view>
		
		<image style="width: 100%; height: 400rpx; margin-top: 80rpx;" mode="aspectFit" src="../../static/fake/activity.png"></image>
		
		<u-row customStyle="margin-top: 50rpx; margin-left: 30rpx;">
			<image style="margin-right: 40rpx; width: 90rpx; height: 90rpx;" mode="aspectFit" src="../../static/icon/weight.png"></image>
			<view>
				<view style="font-size: 34rpx; font-weight: bold;">Total training hours</view>
				<view style="color: #808080; margin-top: 10rpx; font-size: 30rpx;">{{ user.hours }} hours</view>
			</view>
		</u-row>
		
		<u-row customStyle="margin-top: 30rpx; margin-left: 30rpx;">
			<image style="margin-right: 40rpx; width: 90rpx; height: 90rpx;" mode="aspectFit" src="../../static/icon/walk.png"></image>
			<view>
				<view style="font-size: 34rpx; font-weight: bold;">Height</view>
				<view style="color: #808080; margin-top: 10rpx; font-size: 30rpx;">{{ user.height * 100 }} cm</view>
			</view>
		</u-row>
		
		<u-row customStyle="margin-top: 30rpx; margin-left: 30rpx;">
			<image style="margin-right: 40rpx; width: 90rpx; height: 90rpx;" mode="aspectFit" src="../../static/icon/eat.png"></image>
			<view>
				<view style="font-size: 34rpx; font-weight: bold;">Weight</view>
				<view style="color: #808080; margin-top: 10rpx; font-size: 30rpx;">{{ user.weight }} kg</view>
			</view>
		</u-row>
		
		<view style="height: 120rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: { },
				monthes: [
					{ name: "April" },
					{ name: "May" },
					{ name: "June" },
					{ name: "July" },
					{ name: "August" },
				],
			}
		},
		
		async onShow() {
			let user = await uni.$u.http.post('/self');
			let res = await uni.$u.http.post("/total-training");
			user.hours = res.hours;
			
			this.user = user;
		},
		
		methods: {
			
		}
	}
</script>

<style>

</style>
