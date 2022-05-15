<template>
	<view>
		<view style="position: absolute; left: 16%; top: 0; width: 81%; padding: 0 1.5%;">
			<u-row customStyle="margin-top: 10vh;">
				<view style="color: #6e6893; font-size: 40rpx; font-weight: bold;">USER REQUESTS</view>
			</u-row>
			
			<view style="margin-top: 5rpx; border-bottom-style: solid; border-color: #c6c2de; border-width: 3rpx; width: 100%;"></view>
			
			<!-- Requests table -->
			<view style="border-radius: 30rpx; margin-top: 30rpx; box-shadow: 0 0 7px -3rpx #888888;;">
				<u-row customStyle="font-weight: bold; padding: 20rpx 0; color: #6e6893; background-color: #f4f2ff; border-radius: 30rpx 30rpx 0 0;">
					<u-col span="1">
						<view style="text-align: center;">ID</view>
					</u-col>
					<u-col span="3">
						<view style="text-align: center;">USER NAME</view>
					</u-col>
					<u-col span="4">
						<view style="text-align: center;">VENUE</view>
					</u-col>
					<u-col span="2">
						<view style="text-align: center;">MAKE TIME</view>
					</u-col>
					<u-col span="1">
						<view style="text-align: center;">TYPE</view>
					</u-col>
					<u-col span="1" customStyle="text-align: center;">
						<view style="text-align: center;">OPERATION</view>
					</u-col>
				</u-row>
				
				<block v-for="item, index in requests">
					<u-row customStyle="color: #606266; border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; padding: 30rpx 0;">
						<u-col span="1">
							<view style="text-align: center;">{{ item.id }}</view>
						</u-col>
						<u-col span="3">
							<view style="text-align: center;">{{ item.username }}</view>
						</u-col>
						<u-col span="4">
							<view style="text-align: center;">{{ item.venue }}</view>
						</u-col>
						<u-col span="2">
							<view style="text-align: center;">{{ formatDateTime(item.makeTime) }}</view>
						</u-col>
						<u-col span="1">
							<view style="text-align: center;">CANCEL</view>
						</u-col>
						<u-col span="1">
							<u-row justify="center">
								<view @click="rejecting=index" style="border-color: #d9d5ec; border-width: 3rpx; border-style: solid; border-radius: 10rpx 0 0 10rpx; padding: 6rpx;">
									<u-icon name="close" color="#606266"></u-icon>
								</view>
								<view @click="accepting=index" style="border-color: #d9d5ec; border-width: 3rpx 3rpx 3rpx 0; border-style: solid; border-radius: 0 10rpx 10rpx 0; padding: 6rpx;">
									<u-icon name="checkmark" color="#606266"></u-icon>
								</view>
							</u-row>
						</u-col>
					</u-row>
				</block>
				<u-row v-if="requests.length==0"
					justify="center"
					customStyle="font-weight: bold; color: #909399; border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; padding: 30rpx 0;"
				>No requests...</u-row>
				
				<!-- TODO: create a new venue -->
				<u-row
					@click="refresh"
					justify="center"
					customStyle="border-style: solid; border-color: #d9d5ec; border-width: 3rpx 0 0 0; background-color: #f4f2ff; border-radius: 0 0 30rpx 30rpx; padding: 20rpx 0;">
					<u-icon name="reload" color="#6e6893"></u-icon>
					<view style="margin-left: 10rpx; color: #6e6893;">Refresh</view>
				</u-row>
			</view>
		</view>
		
		<u-popup :show="rejecting!==''" @close="rejecting=''" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #f56c6c; font-size: 40rpx; font-weight: bold;">INFO</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">Are your sure you want to reject this request?</view>
				
				<u-row>
					<u-button @click="rejecting=''" text="Cancel" customStyle="margin-left: 50%; color: #909399; border-radius: 15rpx;"></u-button>
					<u-button @click="reject" text="Reject" color="#f56c6c" customStyle="margin-left: 5%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<u-popup :show="accepting!==''" @close="accepting=''" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #3c9cff; font-size: 40rpx; font-weight: bold;">INFO</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">Are your sure you want to accept this request?</view>
				
				<u-row>
					<u-button @click="accepting=''" text="Cancel" customStyle="margin-left: 50%; color: #909399; border-radius: 15rpx;"></u-button>
					<u-button @click="accept" text="Accept" type="primary" customStyle="margin-left: 5%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<!--
		 - Side tool bar
		-->
		<view style="position: fixed; width: 16%; height: 100%; border-right-style: solid; border-color: #e2e2e2; border-width: 3rpx;">
			<u-row customStyle="margin-top: 120rpx;">
				<image
					src="../../static/icon/logo.png"
					mode="aspectFit"
					style="height: 120rpx; width: 120rpx; margin-left: 12%;"
				></image>
				<view>
					<view
						style="color: #6d6d6d; font-weight: bold; font-size: 40rpx; margin-left: 10%;"
					>Redwhale</view>
					
					<view
						style="color: #a6a6a6; margin-left: 10%; white-space: nowrap; font-size: 30rpx;"
					>Growth & Marketing</view>
				</view>
			</u-row>
			
			<u-row @click="gotoVenues" customStyle="margin-top: 25%;">
				<image
					src="../../static/icon/venues.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="font-weight: bold; margin-left: 30rpx; color: #999;">Sports Grounds</view>
			</u-row>
			
			<u-row customStyle="margin-top: 10%;">
				<image
					src="../../static/icon/user-request-active.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="font-weight: bold; margin-left: 30rpx;">User Requests</view>
			</u-row>
			
			<block v-if="auth=='manager'">
				<u-row @click="gotoEmployee" customStyle="margin-top: 10%;">
					<image
						src="../../static/icon/employee.png"
						mode="aspectFit"
						style="height: 40rpx; width: 40rpx; margin-left: 15%;"
					></image>
					<view style="font-weight: bold; margin-left: 30rpx; color: #999;">Employees</view>
				</u-row>
			</block>
			
			<view style="margin: 40% 0 0 15%; border-width: 3rpx; border-color: #e2e2e2; width: 70%; border-bottom-style: solid;"></view>
			
			<u-row @click="showLogout=true" customStyle="margin-top: 3%;">
				<image
					src="../../static/icon/logout.png"
					mode="aspectFit"
					style="height: 40rpx; width: 40rpx; margin-left: 15%;"
				></image>
				<view style="margin-top: 60rpx; font-weight: bold; margin-left: 30rpx;">Logout</view>
			</u-row>
		</view>
		
		<u-popup :show="showLogout" @close="showLogout=false" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #2979ff; font-size: 40rpx; font-weight: bold;">INFO</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">Are your sure you want to logout?</view>
				
				<u-row>
					<u-button @click="showLogout=false" text="Cancel" customStyle="margin-left: 50%; color: #909399; border-radius: 15rpx;"></u-button>
					<u-button @click="logout" text="Logout" type="primary" customStyle="margin-left: 5%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
		
		<u-popup :show="successInfo!=''" @close="successInfo=''" round="30rpx" mode="center">
			<view style="padding: 30rpx; width: 20vw;">
				<view style="color: #2979ff; font-size: 40rpx; font-weight: bold;">SUCCESS</view>
				<view style="margin-top: 10rpx; color: #909399; margin-bottom: 30rpx;">
					{{ successInfo }}
				</view>
				
				<u-row>
					<u-button @click="successInfo=''" text="OK" type="primary" customStyle="margin-top: 40rpx; margin-left: 50%; border-radius: 15rpx;"></u-button>
				</u-row>
			</view>
		</u-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				auth: 'employee',
				showLogout: false,
				successInfo: '',
				
				requests: [ ],
				
				rejecting: '',
				accepting: '',
			}
		},
		
		async onLoad() {
			let auth = await uni.$u.http.post("/check-authority");
			this.auth = auth.authority;
			
			let requests = await uni.$u.http.post("/user-request-list");
			
			for(let i in requests)
			{
				let res = await uni.$u.http.post("/reserved-venue", { }, { params: { reservationId: requests[i].reservationId } });
				requests[i].venue = res.displayName;
				res = await uni.$u.http.post("/of-user", { }, { params: { userId: requests[i].userId } });
				requests[i].username = res.displayName;
			}
			
			this.requests = requests;
		},
		
		methods: {
			async reject(index) {
				let res = await uni.$u.http.post(
					"/reject-request",
					{ },
					{
						params: {
							requestId: this.requests[this.rejecting].id
						}
					}
				);
				
				if(res.status == 200)
				{
					this.successInfo = "Successfully reject the request!";
					this.requests.splice(this.rejecting, 1);
				}
				else uni.showToast({
					title: res.message,
					icon:"error",
					duration: 3000
				});
				
				this.rejecting = '';
			},
			
			async accept(index) {
				let res = await uni.$u.http.post(
					"/accept-request",
					{ },
					{
						params: {
							requestId: this.requests[this.accepting].id
						}
					},
				);
				
				if(res.status == 200)
				{
					this.successInfo = "Successfully accept the request";
					this.requests.splice(this.accepting, 1);
				}
				else uni.showToast({
					title: res.message,
					icon:"error",
					duration: 3000
				});
				
				this.accepting = '';
			},
			
			gotoVenues() {
				uni.$u.route({
					url: "pages/sub/manage",
					type: "reLaunch"
				});
			},
			
			gotoEmployee() {
				uni.$u.route({
					url: "pages/sub/employee",
					type: "reLaunch"
				});
			},
			
			logout() {
				uni.removeStorageSync("token");
				uni.$u.route({
					url: "pages/auth/login",
					type: "reLaunch"
				});
			},
			
			formatDateTime(time) {
				let date = new Date(time);
				let hour = date.getHours();
				let min = date.getMinutes();
				return(
					(date.getMonth() + 1)
					+ "/" + date.getDay()
					+ "/" + date.getFullYear()
					+ "-" + (hour < 10 ? '0' + hour : hour)
					+ ":" + (min < 10 ? '0' + min : min)
				);
			},
			
			refresh() {
				uni.$u.route({
					url: "pages/index/middle",
					type: "redirect",
					params: {
						url: "pages/sub/user-request"
					}
				});
			}
		}
	}
</script>

<style lang="scss" scoped>

</style>
