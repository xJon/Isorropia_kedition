 package fr.wind_blade.isorropia.common.entities;
 
 import java.util.ArrayList;
 import java.util.List;
 import net.minecraft.entity.EntityCreature;
 import net.minecraft.entity.EntityLiving;
 import net.minecraft.entity.ai.EntityAIBase;
 import net.minecraft.entity.ai.EntityAIEatGrass;
 import net.minecraft.entity.ai.EntityAILeapAtTarget;
 import net.minecraft.entity.ai.EntityAILookIdle;
 import net.minecraft.entity.ai.EntityAIPanic;
 import net.minecraft.entity.ai.EntityAISwimming;
 import net.minecraft.entity.ai.EntityAITempt;
 import net.minecraft.entity.ai.EntityAIWander;
 import net.minecraft.entity.ai.EntityAIWatchClosest;
 import net.minecraft.entity.monster.EntitySpider;
 import net.minecraft.entity.player.EntityPlayer;
 import net.minecraft.init.Items;
 import net.minecraft.init.SoundEvents;
 import net.minecraft.item.ItemStack;
 import net.minecraft.network.datasync.DataParameter;
 import net.minecraft.network.datasync.DataSerializers;
 import net.minecraft.network.datasync.EntityDataManager;
 import net.minecraft.util.math.BlockPos;
 import net.minecraft.world.EnumDifficulty;
 import net.minecraft.world.IBlockAccess;
 import net.minecraft.world.World;
 import net.minecraftforge.common.IShearable;
 
 public class EntitySheeder
   extends EntitySpider
   implements IShearable {
   private int sheepTimer;
    private static final DataParameter<Boolean> SHEER = EntityDataManager.createKey(EntitySheeder.class, DataSerializers.BOOLEAN);
 
   
   public EntitySheeder(World p_i1743_1_) {
      super(p_i1743_1_);
   }
 
   
   public void entityInit() {
     super.entityInit();
      this.dataManager.register(SHEER, Boolean.valueOf(false));
   }
 
   
   protected void initEntityAI() {
      this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
      this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 0.5D));
      this.tasks.addTask(2, (EntityAIBase)new EntityAITempt((EntityCreature)this, 0.44D, Items.WHEAT, false));
      this.tasks.addTask(3, (EntityAIBase)new EntityAIEatGrass((EntityLiving)this));
      this.tasks.addTask(4, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4F));
     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
      this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
   }
 
   
   public void onUpdate() {
      super.onUpdate();
      if (this.isDead && !this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && 
        getHealth() > 0.0F) {
/*  64 */       this.isDead = false;
     }
   }
 
   
   public void onLivingUpdate() {
/*  70 */     if (this.world.isRemote) {
        this.sheepTimer = Math.max(0, this.sheepTimer - 1);
     }
      super.onLivingUpdate();
   }
 
   
   public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
      ArrayList<ItemStack> ret = new ArrayList<>();
      setSheared(true);
/*  80 */     for (int i = 2 + this.rand.nextInt(4), j = 0; j < i; j++)
        ret.add(new ItemStack(Items.STRING, 1, 0)); 
      playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
      return ret;
   }
   
   public void setSheared(boolean isShearing) {
      this.dataManager.set(SHEER, Boolean.valueOf(isShearing));
   }
   
   public boolean getSheared() {
      return ((Boolean)this.dataManager.get(SHEER)).booleanValue();
   }
 
   
   public void eatGrassBonus() {
/*  96 */     setSheared(false);
   }
 
   
   public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
      return (!item.isEmpty() && item.getItem() == Items.SHEARS && !getSheared());
   }
 }


/* Location:              E:\recaf\233.jar!\fr\wind_blade\isorropia\common\entities\EntitySheeder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */