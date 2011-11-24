Web::Application.routes.draw do

  get "parts/show"

  get "parts/list"

  get "parts/delete"

  get "parts/search"

  get "parts/create"

  get "parts/edit"

  get "programmes/show"

  get "programmes/list"

  get "programmes/delete"

  get "programmes/search"

  get "programmes/create"

  get "programmes/edit"

  get "courses/index"
  
  get "courses/create"
  
  get "courses/update"
  
  get "courses/destroy"

  #scaffolds  resources :programmes
  get "home/index"
  
  # TODO: root pathing better
  root :to => "home#index" #, :locale => /en|es|fr/
  
  #match "/" => "home#index" #root
  match "logout" => "sessions#destroy", :as => "logout"
  match "login" => "sessions#new", :as => "login"
  match "signup" => "users#new", :as => "signup"
  
  match "courses" => "courses#index", :as => "courses"
  
  resources :users
  resources :sessions
  resources :password_resets
  
  # scope "(:locale)", :locale => /en|es|fr/ do
  # 
  #   end
  # The priority is based upon order of creation:
  # first created -> highest priority.

  # Sample of regular route:
  #   match 'products/:id' => 'catalog#view'
  # Keep in mind you can assign values other than :controller and :action

  # Sample of named route:
  #   match 'products/:id/purchase' => 'catalog#purchase', :as => :purchase
  # This route can be invoked with purchase_url(:id => product.id)

  # Sample resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Sample resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Sample resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Sample resource route with more complex sub-resources
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', :on => :collection
  #     end
  #   end

  # Sample resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end

  # You can have the root of your site routed with "root"
  # just remember to delete public/index.html.
  # root :to => 'welcome#index'

  # See how all your routes lay out with "rake routes"

  # This is a legacy wild controller route that's not recommended for RESTful applications.
  # Note: This route will make all actions in every controller accessible via GET requests.
  # match ':controller(/:action(/:id(.:format)))'
end
