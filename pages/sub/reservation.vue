<template>
	<view style="width: 90vw; margin-left: 5vw;">
		<view style="text-align: center; font-size: 50rpx; font-weight: bold; margin-top: 30rpx; margin-bottom: 40rpx; color: #aaa;">
			Reservation records
		</view>
		<view v-for="item, index in reservations" :key="index" style="margin-top: 30rpx;">
			<u-row @click="gotoReservationDetails(item)">
				<u-col span="10">
					<u-row>
						<view style="font-size: 34rpx; font-weight: bold;">{{ item.venue.displayName }}</view>
					</u-row>
					<u-row>
						<view style="font-size: 28rpx; color: #888; margin: 10rpx 0 10rpx 0;">{{ formatDateTime(item) }}</view>
					</u-row>
				</u-col>
				<u-col span="2">
					<block v-if="item.endTime > new Date().getTime()">
						<view class="reservation-status" v-if="item.status==0" style="color: #ff9900;">Waiting</view>
						<view class="reservation-status" v-else style="color: #19be6b;">Ontime</view>
					</block>
					<block v-else>
						<view class="reservation-status" v-if="item.status==0" style="color: #fa3534;">Overdue</view>
						<view class="reservation-status" v-else style="color: #2979ff;">Finish</view>
					</block>
				</u-col>
			</u-row>
			<u-divider class="divisor"></u-divider>
		</view>
		
		<block v-if="reservations.length==0">
			<view style="color: #888; text-align: center;">
				No record...
			</view>
			<u-divider class="divisor" customStyle="width: 20%; margin: 0 auto;"></u-divider>
		</block>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				reservations: [ ]
			}
		},
		
		async onLoad() {
			let reservations = await uni.$u.http.post("/reservations");
			console.log(reservations);
			
			// Get details of the venues
			for(let i in reservations)
				reservations[i].venue = await uni.$u.http.post("/venue", { }, { params: { venueId: reservations[i].venueId } });
			
			this.reservations = reservations;
		},
		
		methods: {
			gotoReservationDetails(reservation) {
				uni.$u.route({
					url: "pages/sub/reservation-details",
					type: "to",
					params: {
						id: reservation.id
					}
				})
			},
			
			formatDateTime(reservation) {
				let start = new Date(reservation.startTime);
				let end = new Date(reservation.endTime);
				return (start.getMonth() + 1) + "/" + start.getDate() + "/" + start.getFullYear() + " " + this.formatTime(start) + " - " + this.formatTime(end);
			},
			
			formatTime(time) {
				let hour = time.getHours();
				let min = time.getMinutes();
				return (hour < 10 ? '0' + hour : hour) + ":" + (min < 10 ? '0' + min : min);
			}
		}
	}
</script>

<style lang="scss" scoped>
	.divisor {
		margin: 0;
	}
	
	.reservation-status {
		font-size: 26rpx;
	}
</style>
