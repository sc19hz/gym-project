<template>
	<view>
		<view class="main-wrapper">
			<view style="color: #10a5f9; font-size: 50rpx; text-align: center; margin-top: 20rpx;">{{ venue.displayName }}</view>
			<u-row>
				<u-col span="4" offset="8">
					<view style="font-size: 26rpx; padding: 30rpx 0 30rpx 0; color: #999;">{{ formatDate(reservation.startTime) }}</view>
				</u-col>
			</u-row>
			
			<view style="padding: 0 10% 0 10%;">
				<u-divider customStyle="margin: 0;"></u-divider>
				
				<view style="color: #999; font-size: 26rpx; margin: 24rpx 0 8rpx 0;">Time Section</view>
				<view style="font-size: 28rpx;">{{ formatTime(reservation.startTime) + ' - ' + formatTime(reservation.endTime) }}</view>
				
				<view style="color: #999; font-size: 26rpx; margin: 24rpx 0 8rpx 0;">Place</view>
				<view style="font-size: 28rpx;">{{ reservation.reservable }}</view>
				
				<view style="color: #999; font-size: 26rpx; margin: 24rpx 0 8rpx 0;">Price</view>
				<view style="font-size: 28rpx;">{{ formatFloat(reservation.cost) + '£' }}</view>
				
				<view v-if="reservation.startTime > new Date().getTime()"
					style="color: #f54; text-align: center; margin-top: 20rpx; font-size: 28rpx;"
					@click="showCancel=true"
				>Cancel</view>
				
				<u-divider customStyle="margin: 20rpx 0 20rpx 0;"></u-divider>
				
				<view style="text-align: center; color: #10a5f9; font-size: 26rpx; margin-bottom: 10rpx;">Scan this barcode!</view>
				<image style="width: 100%; height: 120rpx;" mode="aspectFit" src="../../static/fake/barcode.png"></image>
			</view>
		</view>
		
		<u-popup :show="showCancel" @close="showCancel=false" mode="center" round="30rpx" customStyle="width: 80%;">
			<view>
				<view style="color: #ff9900; font-size: 40rpx; margin: 20rpx 0 0 30rpx;">WARNING</view>
				<view style="margin: 10rpx 20rpx 20rpx 30rpx; color: #909399;">
					This operation can not be undo! After confirm a cancel request will be sent to the venue manager to check it.
				</view>
				<u-row>
					<u-col span="6">
						<u-button @click="showCancel=false" customStyle="border-radius: 0 0 0 30rpx; border-style: solid none none solid">Cancel</u-button>
					</u-col>
					<u-col span="6">
						<u-button @click="confirmCancel" type="warning" customStyle="border-radius: 0 0 30rpx 0; border-style: none;">Confirm</u-button>
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
				reservation: { },
				venue: { },
				showCancel: false,
			}
		},
		
		async onLoad(params) {
			this.reservation = await uni.$u.http.post("/reservation", { }, { params: { reservationId: params.id } });
			this.reservation.reservable = "default"; // TODO
			
			this.venue = await uni.$u.http.post("/venue", { }, { params: { venueId: this.reservation.venueId } });
		},
		
		methods: {
			async confirmCancel() {
				this.showCancel = false;
				
				let res = await uni.$u.http.post(
					"/cancel-request",
					{ },
					{
						params: {
							reservationId: this.reservation.id
						}
					},
				);
				
				if(res.status == 200)
					uni.showToast({
						title: "Request has been sent out!",
						duration: 2000
					});
				else uni.showToast({
					title: "An error has occurred, please try again later!",
					icon: "error",
					duration: 2000
				});
			},
			
			formatFloat(value) {
				let v = value + '';
				return !v.match(/\.[0-9]+/) ? v + '.0' : v;
			},
			
			formatDate(time) {
				let date = new Date(time);
				let str = date.toDateString().split(" ");
				return str[0] + "," + date.getDate() + " " + str[1];
			},
			
			formatTime(time) {
				let date = new Date(time);
				let hour = date.getHours();
				let min = date.getMinutes();
				return (hour < 10 ? '0' + hour : hour) + ":" + (min < 10 ? '0' + min : min);
			}
		}
	}
</script>

<style lang="scss" scoped>
	
	.main-wrapper {
		border-radius: 30rpx 30rpx 30rpx 30rpx;
		padding: 30rpx 0 30rpx 0;
		width: 80vw;
		margin-left: 10vw;
		margin-top: 5vw;
		box-shadow: 0 -8rpx 10rpx 4rpx #bbb;
	}
</style>
