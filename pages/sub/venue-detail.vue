<template>
	<view>
		<view class="main-wrapper">
			<u-swiper radius="30rpx 30rpx 0rpx 0rpx" :list="swiperImg" indicator indicatorMode="line" height="40vw" circular></u-swiper>
			<view class="title">{{ venue.displayName }}</view>
			
			<u-row class="location">
				<u-icon color="#bbb" customStyle="margin-left: 10rpx" name="map-fill"></u-icon>
				<text style="margin-left: 8rpx;">{{ venue.location }}</text>
			</u-row>
			
			<u-row class="location">
				<u-icon color="#bbb" customStyle="margin-left: 10rpx" name="account-fill"></u-icon>
				<text style="margin-left: 8rpx;">{{ venue.capacity + ' people - max' }}</text>
			</u-row>
			
			<u-row class="location">
				<u-icon color="#bbb" customStyle="margin-left: 10rpx" name="calendar-fill"></u-icon>
				<text style="margin-left: 8rpx;">{{ timeSection.startTime + ' - ' + timeSection.endTime }}</text>
			</u-row>
			
			<u-divider></u-divider>
			
			<view class="comment-title">Comments</view>
			<view v-if="comments.length==0" style="text-align: center; margin-top: 60rpx; margin-bottom: 60rpx; color: #bbb;">No Comment...</view>
			<block v-else>
				<!-- list comments here -->
			</block>
		</view>
		
		<view style="position: fixed; bottom: 0; width: 100%; box-shadow: 0 -5rpx 10rpx #bbb">
			<u-row class="bottom-bar">
				<u-col span="8">
					<view style="margin-left: 20rpx; font-weight: bold; color: #3c9cff;">
						{{ (priceSection.min!=priceSection.max?formatFloat(priceSection.min)+'-':'')+formatFloat(priceSection.max) }}£ / hour
					</view>
				</u-col>
				<u-col span="4">
					<u-button @click="pickDate=true" type="primary" text="Select Time" size="large" customStyle="border-radius: 0;"></u-button>
				</u-col>
			</u-row>
		</view>
		
		<!-- Day picker -->
		<u-popup :show="pickDate" @close="pickDate=false">
			<u-button
				text="Pick a day"
				color="#eee"
				size="large"
				customStyle="border-radius: 0; border-top-style: none; color: #ccc; font-weight: bold;"
			></u-button>
			
			<u-button v-for="item, index in daysAllow" :key="index"
				@click="selectDate(item, index)"
				:text="item"
				icon="calendar"
				size="large"
				customStyle="border-radius: 0; border-top-style: none;"
			></u-button>
		</u-popup>
		
		<!-- Do reservation model -->
		<u-popup :show="pickTimeSection" @close="pickTimeSection=false">
			<u-button
				@click="rePickDate"
				:text="dayPickText"
				icon="calendar"
				iconColor="#ccc"
				color="#eee"
				size="large"
				customStyle="border-radius: 0; border-top-style: none; color: #ccc; font-weight: bold;"
			></u-button>
			
			<u-button v-for="item, index in timeBlocks" :key="index"
				@click="selectTimeBlock(item, index)"
				:disabled="item.capacity <= item.reserved || item.startTime < new Date().getTime()"
				:text="formatTime(item.startTime) + ' - ' + formatTime(item.endTime) + (item.startTime < new Date().getTime() ? '(missed)' : item.capacity <= item.reserved ? '(full)' : '')"
				icon="clock"
				:type="item.selected ? 'primary' : 'info'"
				size="large"
				:customStyle="'border-radius: 0; border-style: none none solid none;' + (item.selected ? 'border-color: #ccc' : '')"
			></u-button>
			
			<u-row>
				<u-col span="8">
					<view style="margin-left: 20rpx; font-weight: bold; color: #3c9cff;">
						{{ bookCost }}£
					</view>
				</u-col>
				<u-col span="4">
					<u-button
						@click="tryBook"
						text="Book!"
						:disabled="bookDisabled"
						type="success"
						size="large"
						customStyle="border-style: none;"
					></u-button>
				</u-col>
			</u-row>
		</u-popup>
		
	</view>
</template>

<script>
	export default {
		data() {
			return {
				timeSection: { "startTime": '00:00', "endTime": '00:00' },
				priceSection: { min: 0, max: 0 },
				
				pickDate: false,
				pickReservable: false,
				pickTimeSection: false,
				
				venue: { },
				swiperImg: [ ],
				comments: [ ],
				
				dayPicked: 0,
				daysAllow: [ "Loading..." ],
				dayPickText: "Wait for day picking...",
				
				timeBlocks: [ ],
				
				bookCost: 0,
				bookDisabled: true,
			}
		},
		
		async onLoad(params) {
			// TODO: merge to one call
			this.swiperImg = await uni.$u.http.post(
				'/venue/swiper-img', { }, { params: { venueId: params.id } },
			);
			
			let timeSection = await uni.$u.http.post('/venue-time-section', { }, { params: { venueId: params.id } });
			this.timeSection.startTime = this.formatTime(timeSection.startTime);
			this.timeSection.endTime = this.formatTime(timeSection.endTime);
			
			this.venue = await uni.$u.http.post(
				'/venue', { }, { params: { venueId: params.id } },
			);
			
			let time = new Date();
			for(let i = 0; i < this.venue.daysAllow; ++i)
			{
				let day = new Date(time.getTime() + i * 24 * 60 * 60 * 1000);
				this.daysAllow[i] = (day.getMonth() + 1) + '/' + day.getDate() + '/' + day.getFullYear();
			}
			
			this.priceSection = await uni.$u.http.post("/price-range", { }, { params: { venueId: this.venue.id } });
		},
		
		methods: {
			formatFloat(value) {
				let v = value + '';
				return !v.match(/\.[0-9]+/) ? v + '.0' : v;
			},
			
			async tryBook() {
				let timeBlocksId = '';
				for(let i in this.timeBlocks)
					if(this.timeBlocks[i].selected)
						timeBlocksId += '_' + this.timeBlocks[i].id;
				
				let res = await uni.$u.http.post(
					'/venue-book',
					{ },
					{
						params: {
							venueId: this.venue.id,
							reservableId: -1,
							day: this.dayPicked,
							timeBlocks: timeBlocksId
						},
					},
				);
				
				this.pickTimeSection = false;
				console.log("book res: ", res);
				if(res.status == 200)
					uni.showToast({
						title: 'Success!',
						duration: 2000
					})
				else uni.showToast({
					title: res.message,
					icon: 'error',
					duration: 2000
				})
			},
			
			rePickDate() {
				this.pickTimeSection = false;
				this.pickDate = true;
			},
			
			formatTime(time) {
				let day = new Date(time);
				let hour = day.getHours();
				let min = day.getMinutes();
				return(
					(hour < 10 ? '0' + hour : hour)
					+ ":" + (min < 10 ? '0' + min : min)
				);
			},
			
			selectTimeBlock(block, index) {
				// Make sure all selected blocks are next to each other
				block.selected = !block.selected;
				let selected = [ ];
				for(let i in this.timeBlocks)
					if(this.timeBlocks[i].selected)
						selected.push(this.timeBlocks[i]);
				selected.sort((a, b) => a.startTime - b.startTime);
				for(let i in selected)
					if(i > 0 && selected[i].startTime != selected[i - 1].endTime)
					{
						uni.showToast({
							title: 'Please make sure the time sections selected are next to each other!',
							icon: 'error',
							duration: 2000
						});
						// this.$u.toast("Please make sure the time sections selected are next to each other!");
						block.selected = !block.selected;
						return;
					}
				this.$set(this.timeBlocks, index, block);
				
				this.bookCost = 0;
				for(let i in selected)
					this.bookCost += selected[i].fee;
				this.bookDisabled = selected.length == 0;
			},
			
			async selectDate(item, index) {
				this.pickDate = false;
				this.dayPicked = index;
				
				this.dayPickText = item + (index == 0 ? '(today)' : index == 1 ? '(tomorrow)' : '');
				
				// If have sub-venues, then select it first
				if(this.venue.capacity < 0)
				{
					
				}
				else
				{
					// Find available time blocks
					this.timeBlocks = await uni.$u.http.post(
						'/available-time-blocks',
						{ },
						{
							params: {
								venueId: this.venue.id,
								reservableId: -1,
								day: index,
							},
						}
					);
					console.log(this.timeBlocks);
					
					// Add field "select" to the blocks
					// for(let i in this.timeBlocks)
					// 	this.timeBlocks[i].selected = false;
					
					this.pickTimeSection = true;
					this.bookDisabled = true;
					this.bookCost = 0;
				}
			},
		}
	}
</script>

<style lang="scss" scoped>
	.bottom-bar {
		// font-size: 40rpx;
	}
	
	.location {
		color: #bbb;
		font: 30rpx;
		margin-top: 10rpx;
	}
	
	.title {
		margin-left: 16rpx;
		margin-top: 30rpx;
		margin-bottom: 20rpx;
		font-size: 40rpx;
	}
	
	.comment-title {
		font-size: 34rpx;
		margin-left: 30rpx;
		font-weight: bold;
		color: #999;
	}
	
	.main-wrapper {
		border-radius: 30rpx 30rpx 30rpx 30rpx;
		padding-bottom: 30rpx;
		width: 80vw;
		margin-left: 10vw;
		margin-top: 5vw;
		box-shadow: 0 -8rpx 10rpx 4rpx #bbb;
	}
	
	.head-swiper {
		border-radius: 30rpx;
	}
</style>
