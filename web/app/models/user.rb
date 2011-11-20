class User < ActiveRecord::Base
  attr_accessible :email, :password, :password_confirmation, :admin
  has_secure_password
  validates_presence_of :password, :on => :create
  # See if a user already exists with this email address first
  validates_uniqueness_of :email, :on => :create
  before_create { generate_token(:auth_token) }
  
  def send_password_reset
    generate_token(:password_reset_token)
    self.password_reset_sent_at = Time.zone.now
    save!
    UserMailer.password_reset(self).deliver
  end
  
  def generate_token(column)
    begin
      self[column] = SecureRandom.hex
    end while User.exists?(column => self[column])
  end
  
end
