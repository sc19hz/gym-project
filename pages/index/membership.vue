<template>
	<view style="width: 80vw; margin-left: 10vw;">
		<view style="text-align: center; font-size: 50rpx; margin: 30rpx 0 30rpx 0;">Get membership now!</view>
		<u-row customStyle="margin-bottom: 10px" justify="center">
			<u--image class="icon" :showLoading="true" shape="circle" width="90rpx" height="90rpx" src="../../static/icon/membership-crown.png"></u--image>
		</u-row>
		<view style="text-align: center; font-size: 34rpx;">Get a discount on every top-up!</view>
		
		<view v-for="item, index in [[50,60],[100,120],[200,240]]" :key="index"
			style="background-color: #5ba6f7; color: #fff; border-radius: 30rpx; padding: 20rpx; margin-top: 50rpx;"
			@click="selectTopup(index)"
		>
			<u-row>
				<u-col span="5">
					<view style="font-size: 50rpx;">{{ item[1] }}£</view>
					<view>Bonus: {{ item[1] - item[0] }}£</view>
				</u-col>
				<u-col span="6" customStyle="padding-right: 10rpx;">
					<view style="text-align: right;">You need to pay:</view>
					<view style="font-size: 50rpx; text-align: right;">{{ item[0] }}£</view>
				</u-col>
				<u-col span="1">
					<view style="width: 20rpx; height: 20rpx; border-radius: 100%; background-color: #fff; margin: 0 auto;"></view>
				</u-col>
			</u-row>
		</view>
		
		<u-popup :show="showConfirm" @close="showConfirm=false" mode="center" round="30rpx" customStyle="width: 80%;">
			<view>
				<view style="color: #5ba6f7; font-size: 40rpx; margin: 20rpx 0 0 30rpx;">INFO</view>
				<view style="margin: 10rpx 20rpx 20rpx 30rpx; color: #909399;">
					This operation can not be undo! After confirm you will jump to the payment window.
				</view>
				<u-row>
					<u-col span="6">
						<u-button @click="showConfirm=false" customStyle="border-radius: 0 0 0 30rpx; border-style: solid none none solid">Cancel</u-button>
					</u-col>
					<u-col span="6">
						<u-button @click="confirmTopup" type="primary" customStyle="border-radius: 0 0 30rpx 0; border-style: none;">Pay!</u-button>
					</u-col>
				</u-row>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				showConfirm: false,
				selectedTopup: 0,
			}
		},
		methods: {
			selectTopup(index) {
				this.selectedTopup = index;
				this.showConfirm = true;
			},
			
			async confirmTopup() {
				this.showConfirm = false;
				
				let res = await uni.$u.http.post(
					'/top-up', { }, { params: { topupId: this.selectedTopup } }
				);
				
				if(res.status == 200)
					uni.showToast({
						title: "Success!",
						duration: 2000
					});
				else
					uni.showToast({
						title: "An unexpected error has occurred, please retry.",
						icon: "error",
						duration: 2000
					});
			},
		}
	}
</script>

<style lang="scss" scoped>
	.icon {
		margin: 0 auto;
		display: block;
	}
</style>
