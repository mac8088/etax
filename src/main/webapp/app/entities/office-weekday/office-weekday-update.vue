<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="etaxApp.officeWeekday.home.createOrEditLabel" v-text="$t('etaxApp.officeWeekday.home.createOrEditLabel')">Create or edit a OfficeWeekday</h2>
                <div>
                    <div class="form-group" v-if="officeWeekday.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="officeWeekday.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeWeekday.officeId')" for="office-weekday-officeId">Office Id</label>
                        <input type="number" class="form-control" name="officeId" id="office-weekday-officeId"
                            :class="{'valid': !$v.officeWeekday.officeId.$invalid, 'invalid': $v.officeWeekday.officeId.$invalid }" v-model.number="$v.officeWeekday.officeId.$model"  required/>
                        <div v-if="$v.officeWeekday.officeId.$anyDirty && $v.officeWeekday.officeId.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeWeekday.officeId.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeWeekday.officeId.number" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeWeekday.cstdWeekworkingDay')" for="office-weekday-cstdWeekworkingDay">Cstd Weekworking Day</label>
                        <input type="text" class="form-control" name="cstdWeekworkingDay" id="office-weekday-cstdWeekworkingDay"
                            :class="{'valid': !$v.officeWeekday.cstdWeekworkingDay.$invalid, 'invalid': $v.officeWeekday.cstdWeekworkingDay.$invalid }" v-model="$v.officeWeekday.cstdWeekworkingDay.$model"  required/>
                        <div v-if="$v.officeWeekday.cstdWeekworkingDay.$anyDirty && $v.officeWeekday.cstdWeekworkingDay.$invalid">
                            <small class="form-text text-danger" v-if="!$v.officeWeekday.cstdWeekworkingDay.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.officeWeekday.cstdWeekworkingDay.maxLength" v-bind:value="$t('entity.validation.maxlength')">
                                This field cannot be longer than 40 characters.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeWeekday.startDate')" for="office-weekday-startDate">Start Date</label>
                        <div class="input-group">
                            <input id="office-weekday-startDate" type="date" class="form-control" name="startDate"  :class="{'valid': !$v.officeWeekday.startDate.$invalid, 'invalid': $v.officeWeekday.startDate.$invalid }"
                            v-model="$v.officeWeekday.startDate.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('etaxApp.officeWeekday.endDate')" for="office-weekday-endDate">End Date</label>
                        <div class="input-group">
                            <input id="office-weekday-endDate" type="date" class="form-control" name="endDate"  :class="{'valid': !$v.officeWeekday.endDate.$invalid, 'invalid': $v.officeWeekday.endDate.$invalid }"
                            v-model="$v.officeWeekday.endDate.$model"  />
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.officeWeekday.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./office-weekday-update.component.ts">
</script>
