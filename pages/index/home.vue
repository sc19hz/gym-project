<template>
	<view style="width: 80vw; margin-left: 10vw;">
	    <view class="promote-0">Find your</view>
		<view class="promote-1">Activity</view>
		<u-row customStyle="margin-bottom: 30rpx">
			<u-col span="6">
				<u-tabs @change="changeSort" lineColor="#000" :list="sortTabs"></u-tabs>
			</u-col>
			<u-col span="6">
				<u-search placeholder="search" v-model="searchKey" :showAction="false" height="50rpx"></u-search>
			</u-col>
		</u-row>
		<view class="entry" v-for="(item, index) in venues" :key="item.id" @click="gotoDetails(item.id)">
			<u-row>
				<image mode="aspectFill" class="entry-img" :src="item.icon"></image>
			</u-row>
			<u-row class="entry-des">
				<u-col span="8">
					<text class="u-line-1" style="font-weight: bold;">{{ item.displayName }}</text>
				</u-col>
				<u-col span="4">
					<u-row justify="end">
						<image v-if="item.hat>2" style="width: 30rpx; height: 30rpx;" src="../../static/icon/hat.png" mode="aspectFit"></image>
						<view class="u-line-1" style="color: #888; margin-left: 10rpx;">{{ item.hat }}</view>
					</u-row>
				</u-col>
			</u-row>
		</view>
		<view style="height: 120rpx;"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchKey: '',
				
				sortTabs: [
					{ name: 'Popular' },
					{ name: 'ALL' },
				],
				
				curPage: 0,
				
				venues: [ ],
			};
		},
		
		async onLoad() {
			this.changeSort({ "index": 0 });
		},
		
		methods: {
			async changeSort(item) {
				let venues = await uni.$u.http.post(
					"/home",
					{ },
					{
						params: {
							pageNum: this.curPage,
							pageSize: 5
						}
					},
				);
				
				for(let i in venues)
				{
					let res = await uni.$u.http.post(
						"/venue-hat",
						{ },
						{
							params: {
								venueId: venues[i].id
							}
						}
					);
					venues[i].hat = res.hat;
				}
				
				venues.sort(item.index == 0 ? (a, b) => b.hat - a.hat : (a, b) => a.id - b.id);
				this.venues = venues;
			},
			
			gotoLogin() {
				uni.$u.route('pages/auth/login')
			},
			
			gotoRegis() {
				uni.$u.route('pages/auth/regis')
			},
			
			gotoDetails(venueId) {
				uni.$u.route({
					url: "pages/sub/venue-detail",
					type: "to",
					params: {
						id: venueId
					}
				})
			}
		},
	}
</script>

<style lang="scss" scoped>
	.entry {
		box-shadow: 0 10rpx 10rpx #bbb;
		border-radius: 30rpx;
		margin-bottom: 30rpx;
		
		.entry-des {
			color: #333;
			font-size: 26rpx;
			height: 8vw;
			border-bottom-left-radius: 30rpx;
			border-bottom-right-radius: 30rpx;
			margin-left: 20rpx;
			margin-right: 20rpx;
		}
		
		.entry-img {
			border-top-left-radius: 30rpx;
			border-top-right-radius: 30rpx;
			width: 80vw;
			height: 40vw;
		}
	}
	
	.promote-0 {
		font-size: 70rpx;
		margin-top: 20rpx;
	}
	
	.promote-1 {
		font-weight: bold;
		font-size: 70rpx;
		margin-bottom: 50rpx;
	}
</style>
